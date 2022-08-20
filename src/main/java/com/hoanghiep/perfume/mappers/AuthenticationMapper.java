package com.hoanghiep.perfume.mappers;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.hoanghiep.perfume.dto.AuthenticationResponse;
import com.hoanghiep.perfume.dto.RegistrationRequest;
import com.hoanghiep.perfume.exception.InputFieldException;
import com.hoanghiep.perfume.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationMapper {

	private final AuthenticationService authService;
	private final UserMapper userMapper;
	
	public AuthenticationResponse login(String email) {
		Map<String, String> resultMap = authService.login(email);
		AuthenticationResponse response = new AuthenticationResponse();
		response.setEmail(resultMap.get("email"));
		response.setRole(resultMap.get("userRole"));
		response.setToken(resultMap.get("token"));
		
		return response;
	}
	
	public boolean signupUser(RegistrationRequest request) {
		return authService.registerUser(userMapper.convertRegistrationToEntity(request));
	}
	public boolean activateUser(String code) {
        return authService.activateUser(code);
    }
	
	public boolean sendPasswordResetCode(String email){
		return authService.sendPasswordResetCode(email);
	}
	public String getEmailByPwResetCode(String code) {
		return authService.findUserEmailByPasswordResetCode(code);
	}
	
	public boolean resetPassword(String email, String password) {
		
		return authService.passwordReset(email, password); 
	}
	
	public boolean editPassword(String email, String password, BindingResult result) {
		if(result.hasErrors()) {
			throw new InputFieldException(result);
		}
		return authService.passwordReset(email, password);
	}
}
