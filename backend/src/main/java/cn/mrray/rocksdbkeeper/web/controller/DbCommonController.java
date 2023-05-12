package cn.mrray.rocksdbkeeper.web.controller;

import cn.mrray.rocksdbkeeper.common.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 通用接口
 * @Author jingye
 * @Time 2023/4/25 11:25
 **/
@RequestMapping("/common")
@RestController
public class DbCommonController {

    /**
     * 连通性测试
     * @return
     */
    @GetMapping("/list")
    public CommonResult getTableNames() {
        return new CommonResult().success();
    }
}
