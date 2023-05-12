package cn.mrray.rocksdbkeeper.web.service;

import cn.mrray.rocksdbkeeper.bean.dto.TableListDto;
import cn.mrray.rocksdbkeeper.bean.vo.DataVo;
import cn.mrray.rocksdbkeeper.common.TableResult;

import java.util.List;

/**
 * @Description TODO
 * @Author jingye
 * @Time 2023/4/21 16:59
 **/
public interface DbOperatorService {

    /**
     * 查询数据库表名称列表
     * @return
     */
    List<String> selectTableNames();

    /**
     * 获取数据库表大小
     * @return
     */
    Long getTableLength(String tableName);

    /**
     * 获取所有表数据
     * @param dto
     * @return
     */
    TableResult<List<DataVo>> getTableData(TableListDto dto);

    /**
     * 通过表名和Key获取Value值
     * @param tableName
     * @param key
     * @return
     */
    String getTableValueByKey(String tableName, String key);
}
