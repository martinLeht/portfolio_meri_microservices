package com.saitama.microservices.authenticationservice.exception;

public class UserLockedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4504815201181154271L;

	public UserLockedException(String msg) {
		super(msg);
	}
}
