package com.saitama.microservices.commonlib.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class ExceptionResponse {
	
	private Date timestamp;
	private String errorCode;
	private String message;
	private String details;	

}
