package com.saitama.microservices.apigateway.error;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpiredException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6019769689697049521L;

	public JwtTokenExpiredException(String msg) {
		super(msg);
	}

}
