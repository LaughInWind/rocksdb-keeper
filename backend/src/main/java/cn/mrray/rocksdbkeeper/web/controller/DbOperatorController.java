package cn.mrray.rocksdbkeeper.web.controller;

import cn.mrray.rocksdbkeeper.bean.dto.TableListDto;
import cn.mrray.rocksdbkeeper.bean.vo.DataVo;
import cn.mrray.rocksdbkeeper.common.CommonResult;
import cn.mrray.rocksdbkeeper.common.TableResult;
import cn.mrray.rocksdbkeeper.web.service.DbOperatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description RocksDB表相关接口
 * @Author jingye
 * @Time 2023/4/21 16:58
 **/
@RequestMapping("/table")
@RestController
public class DbOperatorController {

    @Resource
    private DbOperatorService dbOperatorService;

    /**
     * 获取数据库表信息
     * @return
     */
    @GetMapping("/list")
    public TableResult<List<String>> getTableNames() {
        return new TableResult().success(dbOperatorService.selectTableNames());
    }

    /**
     * 查询表大小
     * @param tableName
     * @return
     */
    @GetMapping("/size")
    public CommonResult<Long> getTableLength(@RequestParam("tableName") String tableName) {
        return new CommonResult().success(dbOperatorService.getTableLength(tableName));
    }

    /**
     * 查询表数据
     * @param dto
     * @return
     */
    @GetMapping("/data/list")
    public TableResult<List<DataVo>> getTableData(TableListDto dto) {
        return dbOperatorService.getTableData(dto);
    }

    /**
     * 根据表key值查询value值
     * @param tableName
     * @param key
     * @return
     */
    @GetMapping("/value")
    public CommonResult<String> getTableValueByKey(@RequestParam("tableName") String tableName, @RequestParam("key") String key) {
        return new CommonResult().success(dbOperatorService.getTableValueByKey(tableName, key));
    }
}
