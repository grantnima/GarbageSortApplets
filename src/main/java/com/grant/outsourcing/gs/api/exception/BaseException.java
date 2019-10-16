package com.grant.outsourcing.gs.api.exception;

import com.grant.outsourcing.gs.constant.IRespCode;

public class BaseException extends Exception {

    private static final long serialVersionUID = -3222145047616133245L;

    protected int code;
    protected String message;
    protected Object data;

    public BaseException() {
    }

    public BaseException(IRespCode respCode) {
        super(respCode.getMessage());
        this.message = respCode.getMessage();
        this.code = respCode.getCode();
    }

    public BaseException(IRespCode respCode, Object data) {
        super(respCode.getMessage());
        this.message = respCode.getMessage();
        this.code = respCode.getCode();
        this.data = data;
    }

    public BaseException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
