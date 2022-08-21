package com.hoanghiep.perfume.service.Impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoanghiep.perfume.entity.User;
import com.hoanghiep.perfume.enums.AuthProvider;
import com.hoanghiep.perfume.enums.Role;
import com.hoanghiep.perfume.exception.ApiRequestException;
import com.hoanghiep.perfume.repositories.UserRepository;
import com.hoanghiep.perfume.security.JwtUtils;
import com.hoanghiep.perfume.service.AuthenticationService;
import com.hoanghiep.perfume.service.SystemAutoMailSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	private final JwtUtils jwtFactory;
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final SystemAutoMailSender systemAutoMailSender;
	
	private final String HOSTNAME = "localhost:3000";
	
	@Override
	public Map<String, String> login(String email) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);
		String userRole = user.getRoles().iterator().next().name();
		String token = jwtFactory.createToken(email, userRole);
		
		Map<String, String> response = new HashMap<>();
		response.put("email", email);
		response.put("token", token);
		response.put("userRole", userRole);
		return response;
	}

	@Override
	public boolean registerUser(User user) {
		// TODO Auto-generated method stub
		User userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb != null) return false;
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setProvider(AuthProvider.LOCAL);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String subject = "Activation code";
        String template = "registration-template";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", user.getFirstName());
        attributes.put("registrationUrl", "http://" + HOSTNAME + "/activate/" + user.getActivationCode());
        systemAutoMailSender.sendMessageHtml(user.getEmail(), subject, template, attributes);
        return true;
	}

	@Override
	public boolean activateUser(String code) {
		 User user = userRepository.findByActivationCode(code);
	     if (user == null) return false;
	        
	     user.setActivationCode(null);
	     user.setActive(true);
	     userRepository.save(user);
	     return true;
	}

	@Override
	@Transactional
	public boolean sendPasswordResetCode(String email) {
		// TODO Auto-generated method stub
		 User user = userRepository.findByEmail(email);
		 
		 if(user == null) {
			 return false;
		 }
		 user.setPasswordResetCode(UUID.randomUUID().toString());
	     userRepository.save(user);

	     String subject = "Password reset";
	     String template = "password-reset-template";
	     Map<String, Object> attributes = new HashMap<>();
	     attributes.put("firstName", user.getFirstName());
	     attributes.put("resetUrl", "http://" + HOSTNAME + "/reset/" + user.getPasswordResetCode());
	     systemAutoMailSender.sendMessageHtml(user.getEmail(), subject, template, attributes);
	     return true;
	}

	
	@Override
	public String findUserEmailByPasswordResetCode(String code) {
		return userRepository.getEmailByPasswordResetCode(code).orElseThrow(()->new ApiRequestException("Email not found.", HttpStatus.NOT_FOUND));
	}
	
	@Override
	public boolean passwordReset(String email, String password) {
		User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordResetCode(null);
        userRepository.save(user);
        return true;
	}
	

}
