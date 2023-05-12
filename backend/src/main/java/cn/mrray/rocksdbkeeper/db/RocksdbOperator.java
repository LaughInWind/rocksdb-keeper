package cn.mrray.rocksdbkeeper.db;


import cn.mrray.rocksdbkeeper.enums.TableEnums;
import cn.mrray.rocksdbkeeper.exception.DbException;
import org.apache.commons.collections.CollectionUtils;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RocksdbOperator implements DatabaseOperator {

    @Value("${rocksdb.path}")
    private String rocksdbPath;

    private static final Logger log = LoggerFactory.getLogger(RocksdbOperator.class);


    private String channel;

    private OptimisticTransactionDB rocksdb;

    private List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();

    private List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();

    private final HashMap<String, ColumnFamilyHandle> columnFamilyIndexMap = new HashMap<>();

    private Transaction txn;

    static {
        RocksDB.loadLibrary();
    }


    @Override
    public synchronized void setChannel(String channel) {
        this.channel = channel;
        initRocksDb();
    }


    @Override
    public synchronized void put(String key, String value) {
        try {
            rocksdb.put(key.getBytes(StandardCharsets.UTF_8.name()), value.getBytes(StandardCharsets.UTF_8.name()));
        } catch (Exception e) {
            throw new DbException("RocksDB put异常", e);
        }
    }

    @Override
    public synchronized void putTable(String table, String key, String value) {
        try {
            ColumnFamilyHandle tableHandle = columnFamilyIndexMap.get(table);
            rocksdb.put(tableHandle, key.getBytes(StandardCharsets.UTF_8.name()), value.getBytes(StandardCharsets.UTF_8.name()));
        } catch (Exception e) {
            throw new DbException("RocksDB get异常", e);
        }
    }


    @Override
    public synchronized String get(String key) {
        try {
            byte[] value = rocksdb.get(key.getBytes(StandardCharsets.UTF_8.name()));
            if (value == null || value.length == 0) {
                return null;
            }
            return new String(value, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new DbException("RocksDB get异常", e);
        }
    }

    @Override
    public synchronized String getTable(String table, String key) {
        try {
            ColumnFamilyHandle tableHandle = columnFamilyIndexMap.get(table);
            byte[] value = rocksdb.get(tableHandle, key.getBytes());
            if (value == null || value.length == 0) {
                return null;
            }
            return new String(value, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new DbException("RocksDB getTable异常", e);
        }
    }


    @Override
    public String getKeyLength(String keyPrefix) {
        long size = 0L;
        try {
            size = rocksdb.getAggregatedLongProperty("rocksdb.estimate-num-keys");
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return String.valueOf(size);
    }

    @Override
    public synchronized void delete(String key) {
        try {
            rocksdb.delete(key.getBytes(StandardCharsets.UTF_8.name()));
        } catch (Exception e) {
            throw new DbException("RocksDB delete异常", e);
        }
    }

    @Override
    public synchronized void deleteTable(String table, String key) {
        try {
            ColumnFamilyHandle tableHandle = columnFamilyIndexMap.get(table);
            rocksdb.delete(tableHandle, key.getBytes(StandardCharsets.UTF_8.name()));
        } catch (Exception e) {
            throw new DbException("RocksDB deleteTable异常", e);
        }
    }


    @Override
    public synchronized void beginTransaction() {
        WriteOptions writeOptions = new WriteOptions();
        txn = rocksdb.beginTransaction(writeOptions);
    }

    @Override
    public synchronized void commitTransaction() {
        try {
            if (txn != null) {
                txn.commit();
                txn.close();
            }
        } catch (Exception e) {
            throw new DbException("RocksDB事务提交异常", e);
        }
    }

    @Override
    public synchronized void rollback() {
        try {
            if (txn != null) {
                txn.rollback();
                txn.close();
            }
        } catch (Exception e) {
            throw new DbException("RocksDB事务回滚异常", e);
        }
    }

    @Override
    public RocksIterator iteratorTable(String table) {
        ColumnFamilyHandle tableHandle = columnFamilyIndexMap.get(table);
        return rocksdb.newIterator(tableHandle);
    }

    /**
     * 获取RocksDB列族名列表
     * @return
     */
    @Override
    public List<String> getTableNames() {
        List<String> tableNames = columnFamilyIndexMap.keySet().stream().sorted().collect(Collectors.toList());
        return tableNames;
    }

    /**
     * 初始化RocksDB
     */
    private void initRocksDb() {
        try (Options options = new Options(); DBOptions dbOptions = new DBOptions()) {
            options.setCreateIfMissing(true);
            dbOptions.setCreateIfMissing(true);
            String dbDataPath = rocksdbPath.concat(channel);
            File file = new File(dbDataPath);
            boolean fileExists = true;
            if (!file.exists()) {
                fileExists = file.mkdirs();
            }
            if (!fileExists) {
                throw new DbException(String.format("创建RocksDB存储文件夹失败,channel=%s", channel));
            }
            List<byte[]> cfs = RocksDB.listColumnFamilies(options, file.getAbsolutePath());
            if (!CollectionUtils.isEmpty(cfs)) {
                for (byte[] cf : cfs) {
                    columnFamilyDescriptors.add(new ColumnFamilyDescriptor(cf, new ColumnFamilyOptions()));
                }
            } else {
                columnFamilyDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));


            }
            rocksdb = OptimisticTransactionDB.open(dbOptions, file.getAbsolutePath(), columnFamilyDescriptors, columnFamilyHandles);


            this.checkInitColumnFamily(cfs);

            //  初始化tableMap
            this.initTableMap();
            log.info("rocksDb init successful，dbDataPath={}", dbDataPath);
        } catch (RocksDBException e) {
            throw new DbException(String.format("打开RocksDB异常,channel=%s", channel), e);
        }
    }

    public OptimisticTransactionDB getRocksdb() {
        return rocksdb;
    }


    public void checkInitColumnFamily(List<byte[]> cfs) throws RocksDBException {
        //  已经存在的列族
        List<String> stringList = cfs.stream()
                .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
                .collect(Collectors.toList());

        //  判断是否创建
        List<String> tableList = TableEnums.getTableList();


        List<String> result = tableList.stream().filter(s -> !stringList.contains(s)).collect(Collectors.toList());


        for (int i = 0; i < result.size(); i++) {

            String tableName = result.get(i);

            String table = TableEnums.getTable(tableName);
            if (table != null) {
                ColumnFamilyHandle tableHandle = rocksdb.createColumnFamily(new ColumnFamilyDescriptor(table.getBytes()));
                columnFamilyHandles.add(tableHandle);
            }
        }
    }

    /**
     * 初始化列族集合
     *
     * @throws RocksDBException
     */
    public void initTableMap() throws RocksDBException {
        for (int i = 0; i < columnFamilyHandles.size(); i++) {
            String tableName = new String(columnFamilyHandles.get(i).getName());
            columnFamilyIndexMap.put(tableName, columnFamilyHandles.get(i));
        }
    }

}
