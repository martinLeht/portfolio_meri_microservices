package com.saitama.microservices.authenticationservice.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6848811817536902130L;
	
	
	public UserNotFoundException(String msg) {
		super(msg);
	}

}
