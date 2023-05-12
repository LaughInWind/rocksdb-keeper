package cn.mrray.rocksdbkeeper.db;

import org.rocksdb.RocksIterator;
import java.util.List;

/**
 * RocksDB操作接口类
 */
public interface DatabaseOperator {

    void setChannel(String channel);

    void put(String key, String value);

    void putTable(String table, String key, String value);

    String get(String key);

    String getTable(String table, String key);

    String getKeyLength(String keyPrefix);

    void delete(String key);

    void deleteTable(String table, String key);

    void beginTransaction();

    void commitTransaction();

    void rollback();

    RocksIterator iteratorTable(String table);

    List<String> getTableNames();

}
