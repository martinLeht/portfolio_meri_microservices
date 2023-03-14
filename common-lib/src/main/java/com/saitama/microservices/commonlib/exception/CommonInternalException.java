package com.saitama.microservices.commonlib.exception;

import org.springframework.http.HttpStatus;

public class CommonInternalException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3316427252659767745L;
	private final HttpStatus status;

	public CommonInternalException(String errorCode, String msg) {
		super(errorCode, msg);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public CommonInternalException(String errorCode, String msg, HttpStatus status) {
		super(errorCode, msg);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

}
