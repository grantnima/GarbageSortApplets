package com.grant.outsourcing.gs.api.exception;

import com.grant.outsourcing.gs.constant.IRespCode;

public class BadRequestException extends BaseException
{
	private static final long serialVersionUID = 1478903470187074824L;

	public BadRequestException(int retCode, String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(retCode, msg, cause, enableSuppression, writableStackTrace);
	}

	public BadRequestException(int retCode, String msg, Throwable cause) {
		super(retCode, msg, cause);
	}

	public BadRequestException(int retCode, String msg) {
		super(retCode, msg);
	}

	public BadRequestException(IRespCode respCode) {
		super(respCode);
	}

	public BadRequestException(IRespCode respCode, Object data) {
		super(respCode, data);
	}
}
