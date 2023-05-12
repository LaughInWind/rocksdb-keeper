package cn.mrray.rocksdbkeeper.common;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @Description 列表接口响应类
 * @Author jingye
 * @Time 2023/4/25 11:28
 **/
public class TableResult<T> {

    /**
     * 响应码，200：成功；500：失败
     */
    private Integer code;

    /**
     * 接口状态信息，操作成功、操作失败
     */
    private String msg;

    /**
     * 接口数据对象列表
     */
    private T list;

    /**
     * 列表长度
     */
    private Integer total;

    /**
     * 分页页码
     */
    private Integer pageNumber = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 10;

    /**
     * 接口状态，true：成功；false: 失败；
     */
    private Boolean success;

    public TableResult() {

    }

    public TableResult(T list) {
        this.list = list;
    }

    public TableResult success() {
        TableResult result = new TableResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("操作成功");
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public TableResult success(T list) {
        TableResult result = this.success();
        result.setList(list);
        result.setTotal(((List) list).size());
        return result;
    }

    public TableResult successPage(T list, Integer total, Integer pageNumber, Integer pageSize) {
        TableResult result = this.success();
        result.setList(list);
        result.setTotal(total);
        result.setPageNumber(pageNumber);
        result.setPageSize(pageSize);
        return result;
    }

    public TableResult<T> success(T list, String msg) {
        TableResult result = this.success(list);
        result.setMsg(msg);
        return result;
    }

    public TableResult<T> error() {
        TableResult result = new TableResult();
        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMsg("操作失败");
        result.setSuccess(Boolean.FALSE);
        return result;
    }

    public TableResult<T> error(T list) {
        TableResult result = this.error();
        result.setList(list);
        return result;
    }

    public TableResult<T> error(T list, String msg) {
        TableResult result = this.error(list);
        result.setMsg(msg);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

