package com.saitama.microservices.commonlib.exception;

public class EntityNotFoundException extends BaseException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5063831477303205384L;

	public EntityNotFoundException(String errorCode, String msg) {
		super(errorCode, msg);
	}

}
