package com.saitama.microservices.authenticationservice.error;

public class RoleMissingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1685484621563425368L;

	public RoleMissingException(String msg) {
		super(msg);
	}
	
}
