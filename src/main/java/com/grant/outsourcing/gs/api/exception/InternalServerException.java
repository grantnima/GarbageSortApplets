package com.grant.outsourcing.gs.api.exception;

import com.grant.outsourcing.gs.constant.ERespCode;
import com.grant.outsourcing.gs.constant.IRespCode;

public class InternalServerException extends BaseException {
    private static final long serialVersionUID = 1333311L;

    public InternalServerException(int retCode, String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(retCode, msg, cause, enableSuppression, writableStackTrace);
    }

    public InternalServerException(int retCode, String msg, Throwable cause) {
        super(retCode, msg, cause);
    }

    public InternalServerException(int retCode, String msg) {
        super(retCode, msg);
    }

    public InternalServerException(IRespCode respCode) {
        super(respCode);
    }

    public InternalServerException() {
        super(ERespCode.INTERNAL_ERROR);
    }
}
