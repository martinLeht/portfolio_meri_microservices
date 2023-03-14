package com.saitama.microservices.commonlib.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6019769689697049521L;

	public JwtTokenMissingException(String msg) {
		super(msg);
	}

}
