package com.saitama.microservices.authenticationservice.exception;

import java.util.Date;

import lombok.Getter;

@Getter
public class ExceptionResponse {
	
	private Date timestamp;
	private String errorCode;
	private String message;
	private String details;
	
	public ExceptionResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	public ExceptionResponse(Date timestamp, String message, String details, String errorCode) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.errorCode = errorCode;
	}
	

}
