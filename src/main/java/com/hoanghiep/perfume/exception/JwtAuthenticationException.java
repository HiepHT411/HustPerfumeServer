package com.hoanghiep.perfume.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

	private HttpStatus status;
	
	public JwtAuthenticationException(String message) {
		super(message);
	}
	
	public JwtAuthenticationException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return this.status;
	}
}
