package com.saitama.microservices.authenticationservice.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.saitama.microservices.authenticationservice.exception.AuthenticationException;
import com.saitama.microservices.authenticationservice.exception.ExceptionResponse;
import com.saitama.microservices.authenticationservice.exception.UserExistsException;
import com.saitama.microservices.authenticationservice.exception.UserLockedException;
import com.saitama.microservices.authenticationservice.exception.UserNotFoundException;
import com.saitama.microservices.authenticationservice.exception.UserNotVerifiedException;
import com.saitama.microservices.commonlib.exception.CommonInternalException;

@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

	@ExceptionHandler(value = CommonInternalException.class)
	public ResponseEntity<ExceptionResponse> handleCommonInternalException(CommonInternalException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false), ex.getErrorCode());
		return new ResponseEntity<ExceptionResponse>(exResponse, ex.getStatus());
	}
	
	@ExceptionHandler(value = AuthenticationException.class)
	public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = UserExistsException.class)
	public ResponseEntity<ExceptionResponse> handleUserExistsException(UserExistsException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = UserLockedException.class)
	public ResponseEntity<ExceptionResponse> handleUserLockedException(UserLockedException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.I_AM_A_TEAPOT);
	}
	
	@ExceptionHandler(value = UserNotVerifiedException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotVerifiedException(UserNotVerifiedException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.UNAUTHORIZED);
	}
}
