package com.hoanghiep.perfume.service.Impl;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hoanghiep.perfume.entity.User;
import com.hoanghiep.perfume.entity.UserPrincipal;
import com.hoanghiep.perfume.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (user.getActivationCode() != null) {
            throw new LockedException("email not activated");
        }
        return UserPrincipal.create(user);
        
		
	}

}
