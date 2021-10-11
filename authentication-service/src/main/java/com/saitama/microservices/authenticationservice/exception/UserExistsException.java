package com.saitama.microservices.authenticationservice.exception;

public class UserExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4504815201181154271L;

	public UserExistsException(String msg) {
		super(msg);
	}
}
