package com.grant.outsourcing.gs.api.exception;

import com.grant.outsourcing.gs.constant.IRespCode;

public class ResourceNotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(int retCode, String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(retCode, msg, cause, enableSuppression, writableStackTrace);
    }

    public ResourceNotFoundException(int retCode, String msg, Throwable cause) {
        super(retCode, msg, cause);
    }

    public ResourceNotFoundException(int retCode, String msg) {
        super(retCode, msg);
    }

    public ResourceNotFoundException(IRespCode respCode) {
        super(respCode);
    }
}
