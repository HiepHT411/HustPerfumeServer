package com.hoanghiep.perfume.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoanghiep.perfume.dto.OrderRequest;
import com.hoanghiep.perfume.dto.OrderResponse;
import com.hoanghiep.perfume.dto.PerfumeResponse;
import com.hoanghiep.perfume.dto.ReviewRequest;
import com.hoanghiep.perfume.dto.UserRequest;
import com.hoanghiep.perfume.dto.UserResponse;
import com.hoanghiep.perfume.entity.UserPrincipal;
import com.hoanghiep.perfume.exception.InputFieldException;
import com.hoanghiep.perfume.mappers.OrderMapper;
import com.hoanghiep.perfume.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hust/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserMapper userMapper;
	private final OrderMapper orderMapper;
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers(){
		return ResponseEntity.ok(userMapper.findAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id){
		
		return ResponseEntity.ok(userMapper.findUserById(id));
	}
	
	@PostMapping("/email")
	public ResponseEntity<UserResponse> getUserByEmail(@RequestBody String email){
		
		return ResponseEntity.ok(userMapper.findUserByEmail(email));
	}
	
	@PutMapping("/edit")
    public ResponseEntity<UserResponse> updateUserInfo(@AuthenticationPrincipal UserPrincipal user,
                                                       @Valid @RequestBody UserRequest request,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        } else {
            return ResponseEntity.ok(userMapper.updateUserProfile(request, user.getEmail()));
        }
    }

    @PostMapping("/cart")
    public ResponseEntity<List<PerfumeResponse>> getCart(@RequestBody List<Long> perfumesIds) {
        return ResponseEntity.ok(userMapper.getCartWithListOfProductId(perfumesIds));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(orderMapper.findOrderByEmail(user.getEmail()));
    }
    
    @PostMapping("/order")
    public ResponseEntity<OrderResponse> postOrder(@Valid @RequestBody OrderRequest order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        } else {
            return ResponseEntity.ok(orderMapper.postOrder(order));
        }
    }
    
    // web socket
//    @PostMapping("/review")
//    public ResponseEntity<PerfumeResponse> addReviewToPerfume(@Valid @RequestBody ReviewRequest review,
//                                                              BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            throw new InputFieldException(bindingResult);
//        } else {
//            PerfumeResponse perfume = userMapper.addReviewToPerfume(review, review.getPerfumeId());
//            messagingTemplate.convertAndSend("/topic/reviews/" + perfume.getId(), perfume);
//            return ResponseEntity.ok(perfume);
//        }
//    }
}
