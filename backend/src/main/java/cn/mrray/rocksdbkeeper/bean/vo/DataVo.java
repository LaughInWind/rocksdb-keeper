package cn.mrray.rocksdbkeeper.bean.vo;

import lombok.*;

/**
 * @Description 数据VO
 * @Author jingye
 * @Time 2023/4/26 17:29
 **/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataVo {

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;
}
