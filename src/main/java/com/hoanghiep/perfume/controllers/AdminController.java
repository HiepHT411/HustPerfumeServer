package com.hoanghiep.perfume.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hoanghiep.perfume.dto.OrderResponse;
import com.hoanghiep.perfume.dto.PerfumeRequest;
import com.hoanghiep.perfume.dto.PerfumeResponse;
import com.hoanghiep.perfume.dto.UserRequest;
import com.hoanghiep.perfume.dto.UserResponse;
import com.hoanghiep.perfume.exception.InputFieldException;
import com.hoanghiep.perfume.mappers.OrderMapper;
import com.hoanghiep.perfume.mappers.PerfumeMapper;
import com.hoanghiep.perfume.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/api/hust/admin")
public class AdminController {
	
	private final UserMapper userMapper;
    private final PerfumeMapper perfumeMapper;
    private final OrderMapper orderMapper;

    @PostMapping("/add")
    public ResponseEntity<PerfumeResponse> addPerfume(@RequestPart(name = "file", required = false) MultipartFile file,
                                                      @RequestPart("perfume") @Valid PerfumeRequest perfume,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        } else {
            return ResponseEntity.ok(perfumeMapper.savePerfume(perfume, file));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<PerfumeResponse> updatePerfume(@RequestPart(name = "file", required = false) MultipartFile file,
                                                         @RequestPart("perfume") @Valid PerfumeRequest perfume,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        } else {
            return ResponseEntity.ok(perfumeMapper.savePerfume(perfume, file));
        }
    }

    @DeleteMapping("/delete/{perfumeId}")
    public ResponseEntity<List<PerfumeResponse>> deletePerfume(@PathVariable(value = "perfumeId") Long perfumeId) {
        return ResponseEntity.ok(perfumeMapper.deletePerfume(perfumeId));
    }

    @GetMapping("/orders")
    //@PreAuthorize(value = "ADMIN")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderMapper.findAllOrders());
    }

    @PostMapping("/order")
    public ResponseEntity<List<OrderResponse>> getUserOrdersByEmail(@RequestBody UserRequest user) {
        return ResponseEntity.ok(orderMapper.findOrderByEmail(user.getEmail()));
    }

    @DeleteMapping("/order/delete/{orderId}")
    public ResponseEntity<List<OrderResponse>> deleteOrder(@PathVariable(value = "orderId") Long orderId) {
        return ResponseEntity.ok(orderMapper.deleteOrderById(orderId));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userMapper.findUserById(userId));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userMapper.findAllUsers());
    }

    
}
