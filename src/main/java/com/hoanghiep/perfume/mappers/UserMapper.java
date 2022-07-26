package com.hoanghiep.perfume.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.hoanghiep.perfume.dto.PerfumeResponse;
import com.hoanghiep.perfume.dto.RegistrationRequest;
import com.hoanghiep.perfume.dto.ReviewRequest;
import com.hoanghiep.perfume.dto.ReviewResponse;
import com.hoanghiep.perfume.dto.UserRequest;
import com.hoanghiep.perfume.dto.UserResponse;
import com.hoanghiep.perfume.entity.Review;
import com.hoanghiep.perfume.entity.User;
import com.hoanghiep.perfume.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
	
	private final ModelMapper modelMapper;
	
	private final UserService userService;
	
	private final PerfumeMapper perfumeMapper;
	
	
	UserResponse convertUserEntityToResponse(User user) {
		return modelMapper.map(user, UserResponse.class);
	}
	
	public UserResponse findUserById(Long id) {
		return convertUserEntityToResponse(userService.findUserById(id));
	}
	
	public UserResponse findUserByEmail(String email) {
		return convertUserEntityToResponse(userService.findUserByEmail(email));
	}
	
	public List<UserResponse> findAllUsers(){
		return userService.findAllUsers().stream()
								.map(this::convertUserEntityToResponse)
								.collect(Collectors.toList());
	}
	
	public List<PerfumeResponse> getCartWithListOfProductId(List<Long> perfumeIds){
		return perfumeMapper.convertListOfEntityToResponse(userService.getUserCart(perfumeIds));
	}
	
	
	User convertUserRequestToEntity(UserRequest request) {
		return modelMapper.map(request, User.class);
	}
	
	public UserResponse updateUserProfile(UserRequest request, String email) {
		return convertUserEntityToResponse(userService.updateProfile(email, convertUserRequestToEntity(request)));
	}
	
	
	public User convertRegistrationToEntity(RegistrationRequest request) {
		return modelMapper.map(request, User.class);
	}
	
	public Review convertReviewRequestToEntity(ReviewRequest request) {
		return modelMapper.map(request, Review.class);
	}
	
	public ReviewResponse addReviewToPerfume(ReviewRequest request, Long perfumeId) {
		Review review = modelMapper.map(request, Review.class);
		
		ReviewResponse res = modelMapper.map(userService.addReviewToPerfume(review, perfumeId), ReviewResponse.class);
		return res;
	}
}
