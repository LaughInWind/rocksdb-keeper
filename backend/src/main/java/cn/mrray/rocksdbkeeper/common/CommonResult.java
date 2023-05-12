package cn.mrray.rocksdbkeeper.common;

import org.springframework.http.HttpStatus;

/**
 * @Description 通用接口响应类
 * @Author jingye
 * @Time 2023/4/25 11:28
 **/
public class CommonResult<T> {

    /**
     * 响应码，200：成功；500：失败
     */
    private Integer code;

    /**
     * 接口状态信息，操作成功、操作失败
     */
    private String msg;

    /**
     * 接口数据对象
     */
    private T data;

    /**
     * 接口状态，true：成功；false: 失败；
     */
    private Boolean success;

    public CommonResult() {

    }

    public CommonResult(T data) {
        this.data = data;
    }

    public CommonResult success() {
        CommonResult result = new CommonResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("操作成功");
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public CommonResult success(T data) {
        CommonResult result = this.success();
        result.setData(data);
        return result;
    }

    public CommonResult<T> success(T data, String msg) {
        CommonResult result = this.success(data);
        result.setMsg(msg);
        return result;
    }

    public CommonResult<T> error() {
        CommonResult result = new CommonResult();
        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMsg("操作失败");
        result.setSuccess(Boolean.FALSE);
        return result;
    }

    public CommonResult<T> error(T data) {
        CommonResult result = this.error();
        result.setData(data);
        return result;
    }

    public CommonResult<T> error(T data, String msg) {
        CommonResult result = this.error(data);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
