package com.hoanghiep.perfume.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.entity.Review;
import com.hoanghiep.perfume.entity.User;
import com.hoanghiep.perfume.repositories.PerfumeRepository;
import com.hoanghiep.perfume.repositories.ReviewRepository;
import com.hoanghiep.perfume.repositories.UserRepository;
import com.hoanghiep.perfume.service.UserService;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	private final PerfumeRepository perfumeRepository;
	
	private final ReviewRepository reviewRepository;
	
	@Override
	public User findUserById(Long id) {
		
		return userRepository.findById(id).get();
	}

	@Override
	public User findUserByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public List<Perfume> getUserCart(List<Long> perfumeIds) {
		
		return perfumeRepository.findByIdIn(perfumeIds);
	}

	@Override
	public User updateProfile(String email, User newUserProfile) {
		User oldUserProfile = userRepository.findByEmail(email);
		
		oldUserProfile.setAddress(newUserProfile.getAddress());
		oldUserProfile.setCity(newUserProfile.getCity());
		oldUserProfile.setFirstName(newUserProfile.getFirstName());
		oldUserProfile.setLastName(newUserProfile.getLastName());
		oldUserProfile.setPhoneNumber(newUserProfile.getPhoneNumber());
		oldUserProfile.setPostIndex(newUserProfile.getPostIndex());
		
		userRepository.save(oldUserProfile);
		
		return oldUserProfile;
	}

	@Override
	public Perfume addReviewToPerfume(Review newReview, Long perfumeId) {
		
		Perfume perfume = perfumeRepository.getById(perfumeId);
		
		List<Review> reviews = perfume.getReviews();
		reviews.add(newReview);
		
		//de type double nham muc dich widenning
		double totalNumberOfReviews = reviews.size();
		double totalRatingPoint = reviews.stream().mapToDouble(Review::getRating).sum();
		
		perfume.setRating(totalRatingPoint/totalNumberOfReviews);
		
		reviewRepository.save(newReview);
		perfumeRepository.save(perfume);
		
		return perfume;
	}

	@Override
	public DataFetcher<User> getUserByQuery() {
		// TODO Auto-generated method stub
		return dataFetchingEnvironment -> {
				Long id = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
				return userRepository.findById(id).get();
		};
	}

	@Override
	public DataFetcher<List<User>> getAllUsersByQuery() {
		// TODO Auto-generated method stub
		return dataFetchingEnvironment -> userRepository.findAllByOrderByIdAsc();
	}

	
	
}
