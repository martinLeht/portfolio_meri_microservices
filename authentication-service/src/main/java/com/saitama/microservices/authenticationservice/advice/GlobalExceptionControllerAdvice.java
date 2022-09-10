package com.saitama.microservices.authenticationservice.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.saitama.microservices.authenticationservice.exception.AuthenticationException;
import com.saitama.microservices.authenticationservice.exception.ExceptionResponse;
import com.saitama.microservices.authenticationservice.exception.JwtTokenMalformedException;
import com.saitama.microservices.authenticationservice.exception.JwtTokenMissingException;
import com.saitama.microservices.authenticationservice.exception.JwtTokenRefreshException;
import com.saitama.microservices.authenticationservice.exception.RoleMissingException;
import com.saitama.microservices.authenticationservice.exception.UserExistsException;
import com.saitama.microservices.authenticationservice.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

	
	@ExceptionHandler(value = AuthenticationException.class)
	public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = RoleMissingException.class)
	public ResponseEntity<ExceptionResponse> handleRoleMissingException(RoleMissingException ex, WebRequest req) {
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
	
	@ExceptionHandler(value = JwtTokenMalformedException.class)
	public ResponseEntity<ExceptionResponse> handleJwtTokenMalformedException(JwtTokenMalformedException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = JwtTokenMissingException.class)
	public ResponseEntity<ExceptionResponse> handleJwtTokenMissingException(JwtTokenMissingException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = JwtTokenRefreshException.class)
	public ResponseEntity<ExceptionResponse> handleJwtRefreshTokenException(JwtTokenRefreshException ex, WebRequest req) {
		ExceptionResponse exResponse= new ExceptionResponse(new Date(), ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exResponse, HttpStatus.FORBIDDEN);
	}
}
