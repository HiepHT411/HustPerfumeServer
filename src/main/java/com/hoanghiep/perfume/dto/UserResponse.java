package com.hoanghiep.perfume.dto;

import java.util.Set;

import com.hoanghiep.perfume.enums.Role;

import lombok.Data;

@Data
public class UserResponse {
	
	private Long id;
    private String email;
    private String firstName;
    private String lastName;
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
