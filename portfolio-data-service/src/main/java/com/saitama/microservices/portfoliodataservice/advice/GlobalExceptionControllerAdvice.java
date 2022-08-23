package com.saitama.microservices.portfoliodataservice.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.saitama.microservices.commonlib.exception.EntityExistsException;
import com.saitama.microservices.commonlib.exception.EntityNotFoundException;
import com.saitama.microservices.commonlib.exception.ExceptionResponse;


@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

	
	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest req) {
		ExceptionResponse exResponse = ExceptionResponse.builder()
				.timestamp(new Date())
				.errorCode(ex.getErrorCode())
				.message(ex.getMessage())
				.details(req.getDescription(false))
				.build();
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = EntityExistsException.class)
	public ResponseEntity<ExceptionResponse> handleEntityExistsException(EntityExistsException ex, WebRequest req) {
		ExceptionResponse exResponse = ExceptionResponse.builder()
				.timestamp(new Date())
				.errorCode(ex.getErrorCode())
				.message(ex.getMessage())
				.details(req.getDescription(false))
				.build();
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.CONFLICT);
	}
}
