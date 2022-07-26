package com.hoanghiep.perfume.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.hoanghiep.perfume.enums.Role;

import lombok.Data;

@Data
public class UserRequest {

	@NotBlank(message = "FirstName cannot be empty")
    private String firstName;

    @NotBlank(message = "LastName cannot be empty")
    private String lastName;

    private String email;
    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
    private String provider;
    private boolean active;
    private String activationCode;
    private String passwordResetCode;
    private Set<Role> roles;
    
}
