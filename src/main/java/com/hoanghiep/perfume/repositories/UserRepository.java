package com.hoanghiep.perfume.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoanghiep.perfume.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findAllByOrderByIdAsc();
	
	User findByActivationCode(String activationCode);
	
	User findByEmail(String email);
	
	//User findByPasswordResetCode(String passwordResetCode);
	
	@Query("SELECT user.email FROM User user WHERE user.passwordResetCode = :code")
    Optional<String> getEmailByPasswordResetCode(String code);
	
}
