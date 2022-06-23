package com.hoanghiep.perfume.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String email;
	private String token;
	private String role;
}
