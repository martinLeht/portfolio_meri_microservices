package com.saitama.microservices.commonlib.exception;

public class CommonInternalException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3316427252659767745L;

	public CommonInternalException(String errorCode, String msg) {
		super(errorCode, msg);
	}

}
