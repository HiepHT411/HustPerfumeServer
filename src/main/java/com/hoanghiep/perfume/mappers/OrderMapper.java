package com.hoanghiep.perfume.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.hoanghiep.perfume.dto.OrderRequest;
import com.hoanghiep.perfume.dto.OrderResponse;
import com.hoanghiep.perfume.entity.Order;
import com.hoanghiep.perfume.service.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMapper {

	private final ModelMapper modelMapper;
	private final OrderService orderService;
	
	
	private Order convertRequestToEntity(OrderRequest request) {
		return modelMapper.map(request, Order.class);
	}
	
	private OrderResponse convertEntityToResponse(Order order) {
		return modelMapper.map(order, OrderResponse.class);
	}
	
	private List<OrderResponse> convertEntityListToResponse(List<Order> orders){
		return orders.stream().map(this::convertEntityToResponse)
							.collect(Collectors.toList());
	}
	
	public List<OrderResponse> findAllOrders(){
		return convertEntityListToResponse(orderService.findAll());
	}
	
	public List<OrderResponse> findOrderByEmail(String email) {
		return convertEntityListToResponse(orderService.findOrderByEmail(email));
	}
	
	public List<OrderResponse> deleteOrderById(Long orderId) {
		return convertEntityListToResponse(orderService.deleteOrder(orderId));
	}
	
	
	public OrderResponse postOrder(OrderRequest orderRequest) {
		return convertEntityToResponse(orderService.postOrder(convertRequestToEntity(orderRequest), orderRequest.getPerfumesId()));
	}
}
