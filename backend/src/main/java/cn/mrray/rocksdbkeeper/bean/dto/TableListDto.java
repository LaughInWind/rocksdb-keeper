package cn.mrray.rocksdbkeeper.bean.dto;

import lombok.*;

/**
 * @Description TODO
 * @Author jingye
 * @Time 2023/4/27 18:46
 **/
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TableListDto {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 键值
     */
    private String key;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 分页索引
     */
    private Integer pageIndex;
}
