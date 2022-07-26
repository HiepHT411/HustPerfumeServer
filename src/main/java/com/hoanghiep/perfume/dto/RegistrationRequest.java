package com.hoanghiep.perfume.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegistrationRequest {
	
	@NotBlank(message = "Fill captcha.")
    private String captcha;

    @NotBlank(message = "FirstName cannot be empty")
    private String firstName;

    @NotBlank(message = "LastName cannot be empty")
    private String lastName;

    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
    private String password;

    @Size(min = 6, max = 16, message = "The password confirmation must be between 6 and 16 characters long")
    private String password2;

    @Email(message = "Incorrect email type")
    @NotBlank(message = "Email cannot be empty")
    private String email;
}
