package com.ecommerce.ecommerceapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex){
		String message = "You don't have permission to access this action";
		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}

}
