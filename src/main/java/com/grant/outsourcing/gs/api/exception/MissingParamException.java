package com.grant.outsourcing.gs.api.exception;

import com.grant.outsourcing.gs.constant.IRespCode;

public class MissingParamException extends BaseException {

    private static final long serialVersionUID = 1525054611697764061L;

    public MissingParamException(int retCode, String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(retCode, msg, cause, enableSuppression, writableStackTrace);
    }

    public MissingParamException(int retCode, String msg, Throwable cause) {
        super(retCode, msg, cause);
    }

    public MissingParamException(int retCode, String msg) {
        super(retCode, msg);
    }

    public MissingParamException(IRespCode respCode) {
        super(respCode);
    }
}
