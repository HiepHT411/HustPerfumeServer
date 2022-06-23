package com.hoanghiep.perfume.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoanghiep.perfume.dto.AuthenticationRequest;
import com.hoanghiep.perfume.dto.AuthenticationResponse;
import com.hoanghiep.perfume.dto.PasswordResetRequest;
import com.hoanghiep.perfume.dto.RegistrationRequest;
import com.hoanghiep.perfume.exception.ApiRequestException;
import com.hoanghiep.perfume.exception.EmailException;
import com.hoanghiep.perfume.exception.InputFieldException;
import com.hoanghiep.perfume.mappers.AuthenticationMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hust/auth")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	
	private final AuthenticationMapper authenticationMapper;
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			return ResponseEntity.ok(authenticationMapper.login(request.getEmail()));
		} catch (AuthenticationException e) {
			throw new ApiRequestException("Incorrect password or email", HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody RegistrationRequest request, BindingResult bindingResult) {
		if(!request.getPassword().equals(request.getPassword2())) {
			throw new ApiRequestException("Password confirmation does not match", HttpStatus.BAD_REQUEST);
		}
		if(bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		}
		if (!authenticationMapper.signupUser(request)) {
            throw new EmailException("This email address is already used.");
        }
        return ResponseEntity.ok("User successfully registered.");
	}
	
	@GetMapping("/activate/{code}")
    public ResponseEntity<String> activateEmailCode(@PathVariable String code) {
        if (!authenticationMapper.activateUser(code)) {
            throw new ApiRequestException("Activation code not found.", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok("User successfully activated.");
        }
    }
	@PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
		if (passwordResetRequest.getPassword().equals(passwordResetRequest.getPassword2())) {
			boolean success = authenticationMapper.forgotPassword(passwordResetRequest.getEmail(), passwordResetRequest.getPassword());
		
			if(success) {
				return ResponseEntity.ok("Password successfully changed!");
			} else {
				throw new ApiRequestException("Email not found", HttpStatus.BAD_REQUEST);
			}
		}else {
			throw new ApiRequestException("Password confirmation does not match", HttpStatus.BAD_REQUEST);
		}
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
}
