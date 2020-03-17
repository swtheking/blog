package com.swpym.blog.api.model;

import org.apache.http.HttpStatus;

/**
 * @description: API接口的基础返回类
 * @author: shaowei
 * @date: 2020-03-09 13:41:40
 */
public class BaseResponse<T> {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 说明
     */
    private String msg;

    /*
     * 错误代码
     */
    private Integer code;
    private static final String SUCCESS_MSG = "成功";
    /**
     * 返回数据
     */
    private T data;

    private BaseResponse(boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, HttpStatus.SC_OK, SUCCESS_MSG, data);
    }

    public static <T> BaseResponse<T> error(int code, String message, T data) {
        return new BaseResponse<>(false, code, message, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

}
