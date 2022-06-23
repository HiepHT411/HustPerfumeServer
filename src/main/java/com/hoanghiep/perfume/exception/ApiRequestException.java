package com.hoanghiep.perfume.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiRequestException extends RuntimeException {
	
	private final HttpStatus httpStatus;
	
	public ApiRequestException(String message, HttpStatus status) {
		super(message);
		this.httpStatus = status;
	}
}
