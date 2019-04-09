package com.wzp.aiplatform.utils;

public enum StatusCode {
    SUCCESS(200, "success"),
    FAILED(-1, "failed"),
    ERROR(500, "Error");


    final int code;
    final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}