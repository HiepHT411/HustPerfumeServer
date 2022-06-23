package com.hoanghiep.perfume.dto;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PasswordResetRequest {

	private String email;				// user's mail for find user, maybe dung de gui new pw hay hon 

    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
    private String password;

    @Size(min = 6, max = 16, message = "The password confirmation must be between 6 and 16 characters long")
    private String password2;
}
