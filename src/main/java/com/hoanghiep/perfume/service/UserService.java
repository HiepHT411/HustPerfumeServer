package com.hoanghiep.perfume.service;

import java.util.List;

import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.entity.Review;
import com.hoanghiep.perfume.entity.User;

import graphql.schema.DataFetcher;

public interface UserService {
	
	User findUserById(Long id);
	
	User findUserByEmail(String email);
	
	List<User> findAllUsers();
	
	List<Perfume> getUserCart(List<Long> perfumeIds);
	
	User updateProfile(String email, User user);
	
	Perfume addReviewToPerfume(Review review, Long perfumeId);

	DataFetcher<User> getUserByQuery();
	
	DataFetcher<List<User>> getAllUsersByQuery();
}
