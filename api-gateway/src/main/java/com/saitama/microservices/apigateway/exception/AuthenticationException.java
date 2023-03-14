package com.saitama.microservices.apigateway.exception;

import org.springframework.http.HttpStatus;

import com.saitama.microservices.commonlib.exception.BaseException;

public class AuthenticationException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482756368162653914L;
	private final HttpStatus status;

	public AuthenticationException(String errorCode, String msg, HttpStatus status) {
		super(errorCode, msg);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
}
