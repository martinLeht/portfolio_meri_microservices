package com.saitama.microservices.commonlib.exception;

public class EntityExistsException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2736894465424798186L;

	public EntityExistsException(String errorCode, String msg) {
		super(errorCode, msg);
	}

}
