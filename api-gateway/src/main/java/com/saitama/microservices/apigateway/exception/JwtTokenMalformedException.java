package com.saitama.microservices.apigateway.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMalformedException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9198531389025243169L;

	public JwtTokenMalformedException(String msg) {
		super(msg);
	}
}
