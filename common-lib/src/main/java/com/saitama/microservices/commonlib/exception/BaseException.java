package com.saitama.microservices.commonlib.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6527767453822179596L;
	private final String errorCode;
	
	public BaseException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
}
