package com.saitama.microservices.commonlib.exception;


public class JwtTokenRefreshException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7979051323140417137L;
	
	private String token;

	public JwtTokenRefreshException(String token, String msg) {
		super(msg);
		this.token = token;
	}
}
