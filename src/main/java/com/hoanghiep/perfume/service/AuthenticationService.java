package com.hoanghiep.perfume.service;

import java.util.Map;

import com.hoanghiep.perfume.entity.User;

public interface AuthenticationService {

	Map<String, String> login(String email);

    boolean registerUser(User user);

    boolean activateUser(String code);

    User findByPasswordResetCode(String code);

    boolean sendPasswordResetCode(String email);

    boolean passwordReset(String email, String password);
}
