package com.wzp.aiplatform.utils;

public final class ApiResult<T> {
    private int code;
    private String msg;
    private T data;

    public ApiResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResult(T data){
        this.code = StatusCode.SUCCESS.getCode();
        this.msg = StatusCode.SUCCESS.getMsg();
        this.data = data;
    }

    public ApiResult(StatusCode statusCode, T data){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    public static <E> ApiResult<E> getApiResult(E data){
        return new ApiResult(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMsg(), data);
    }
    public static <E> ApiResult<E> getApiResult(String message, E data){
        return new ApiResult(StatusCode.SUCCESS.getCode(), message, data);
    }
    public static <E> ApiResult<E> getApiResult(int code, String message) {
        return new ApiResult(code, message, null);
    }

    public static <E> ApiResult<E> getApiResult(StatusCode statusCode, E data){
        return new ApiResult(statusCode.getCode(), statusCode.getMsg(), data);
    }

    public static <E> ApiResult<E> getFailedApiResult(E data){
        return new ApiResult(StatusCode.FAILED.getCode(), StatusCode.FAILED.getMsg(), data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
}