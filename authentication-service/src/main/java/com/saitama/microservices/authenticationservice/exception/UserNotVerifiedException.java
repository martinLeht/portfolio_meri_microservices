package com.saitama.microservices.authenticationservice.exception;

public class UserNotVerifiedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4504815201181154271L;

	public UserNotVerifiedException(String msg) {
		super(msg);
	}
}
