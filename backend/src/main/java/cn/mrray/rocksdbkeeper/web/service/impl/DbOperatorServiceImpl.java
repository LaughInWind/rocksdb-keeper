package cn.mrray.rocksdbkeeper.web.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.mrray.rocksdbkeeper.bean.dto.TableListDto;
import cn.mrray.rocksdbkeeper.bean.vo.DataVo;
import cn.mrray.rocksdbkeeper.common.TableResult;
import cn.mrray.rocksdbkeeper.db.DatabaseOperator;
import cn.mrray.rocksdbkeeper.web.service.DbOperatorService;
import org.rocksdb.RocksIterator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 数据库操作接口实现类
 * @Author jingye
 * @Time 2023/4/21 17:03
 **/
@Service
public class DbOperatorServiceImpl implements DbOperatorService {

    /**
     * 存储表大小，Map<表名，表长度>
     */
    private static Map<String, Long> tableSizeMap = new HashMap<>(4);

    /**
     * 存储表数据，Map<表名，表数据>
     */
    private static Map<String, List<DataVo>> tableDataMap = new HashMap<>(4);

    @Resource
    private DatabaseOperator databaseOperator;

    /**
     * 查询数据库内所有表名
     * @return
     */
    @Override
    public List<String> selectTableNames() {
        return databaseOperator.getTableNames();
    }

    /**
     * 查询表大小
     * @param tableName
     * @return
     */
    @Override
    public Long getTableLength(String tableName) {
        if (tableSizeMap.containsKey(tableName)) {
            return tableSizeMap.get(tableName);
        }

        Long tableSize = 0L;
        RocksIterator iterator = databaseOperator.iteratorTable(tableName);
        for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
            if (iterator.key() != null) {
                tableSize++;
            }
        }

        tableSizeMap.put(tableName, tableSize);
        return tableSize;
    }

    /**
     * 查询表内数据
     * @param dto
     * @return
     */
    @Override
    public TableResult<List<DataVo>> getTableData(TableListDto dto) {
        if (StrUtil.isBlank(dto.getTableName())) {
            return new TableResult<>().success(new ArrayList<>());
        }

        // 根据key值查询结果
        if (StrUtil.isNotBlank(dto.getTableName()) && StrUtil.isNotBlank(dto.getKey())) {
            String value = this.getTableValueByKey(dto.getTableName(), dto.getKey());
            List<DataVo> list = new ArrayList<>(2);
            if (StrUtil.isNotBlank(value)) {
                DataVo dataVo = DataVo.builder().key(dto.getKey()).value(value).build();
                list.add(dataVo);
            }
            return new TableResult<>().success(list);
        }

        // 查询所有表内数据
        if (tableDataMap.containsKey(dto.getTableName())) {
            return pageUtil(dto, tableDataMap.get(dto.getTableName()));
        }

        List<DataVo> tableDataList = new ArrayList<>(8);
        RocksIterator iterator = databaseOperator.iteratorTable(dto.getTableName());
        for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
            if (iterator.key() != null) {
                DataVo vo = DataVo.builder().key(new String(iterator.key())).value(new String(iterator.value())).build();
                tableDataList.add(vo);
            }
        }

        tableDataMap.put(dto.getTableName(), tableDataList);
        return pageUtil(dto, tableDataList);
    }

    /**
     * 根据表key值获取value值
     * @param tableName
     * @param key
     * @return
     */
    @Override
    public String getTableValueByKey(String tableName, String key) {
        return databaseOperator.getTable(tableName, key);
    }

    /**
     * 逻辑分页，适用于数据量不大，更新不频繁的场景，可以提高检索速度，从磁盘一次读取，即可满足前端多次查询
     * @return
     */
    private TableResult<List<DataVo>> pageUtil(TableListDto dto, List<DataVo> tableDataList) {
        // 当前页数据列表
        List<DataVo> pageResult = tableDataList.stream()
                .skip((dto.getPageIndex() - 1L) * dto.getPageSize()).limit(dto.getPageSize()).collect(Collectors.toList());
        return new TableResult<>().successPage(pageResult, tableDataList.size(), dto.getPageIndex(), dto.getPageSize());
    }
}
