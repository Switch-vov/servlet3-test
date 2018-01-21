package com.switchvov.servlet3test.common;

public class ResponseResult<T> {

    private int code = ResponseCode.SUCCESS.getCode();

    private String msg = ResponseCode.SUCCESS.getMsg();

    private T data;

    public ResponseResult() {

    }

    public ResponseResult(T data) {
        this.data = data;
    }

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<T>();
    }

    public static <T> ResponseResult<T> successWithData(T data) {
        return new ResponseResult<>(data);
    }

    public static <T> ResponseResult<T> failWithCodeAndMsg(int code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }

    public static <T> ResponseResult<T> buildWithCode(ResponseCode code) {
        return new ResponseResult<>(code.getCode(), code.getMsg(), null);
    }
}
