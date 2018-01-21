package com.switchvov.servlet3test.common;

public enum ResponseCode {

    SUCCESS(0, "成功")
    ;


    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public final int code;
    public final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
