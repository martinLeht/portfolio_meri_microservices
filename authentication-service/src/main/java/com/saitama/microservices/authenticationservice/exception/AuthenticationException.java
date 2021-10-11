package com.saitama.microservices.authenticationservice.exception;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8482756368162653914L;

	public AuthenticationException(String msg) {
		super(msg);
	}
}
