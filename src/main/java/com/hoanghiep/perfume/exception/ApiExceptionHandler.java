package com.hoanghiep.perfume.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(ApiRequestException.class)
	public ResponseEntity<String> apiRequestExceptionHandler(ApiRequestException e){
		return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
	}
}
