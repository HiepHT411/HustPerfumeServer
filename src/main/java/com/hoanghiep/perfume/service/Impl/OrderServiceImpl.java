package com.hoanghiep.perfume.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hoanghiep.perfume.entity.Order;
import com.hoanghiep.perfume.entity.OrderItem;
import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.repositories.OrderItemRepository;
import com.hoanghiep.perfume.repositories.OrderRepository;
import com.hoanghiep.perfume.repositories.PerfumeRepository;
import com.hoanghiep.perfume.service.OrderService;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PerfumeRepository perfumeRepository;
    
	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		return orderRepository.findAllByOrderByIdAsc();
	}

	@Override
	public List<Order> findOrderByEmail(String email) {
		// TODO Auto-generated method stub
		return orderRepository.findOrderByEmail(email);
	}

	@Override
	@Transactional
	public Order postOrder(Order validOrder, Map<Long, Long> perfumesId) {
		Order order = new Order();
		List<OrderItem> orderItemList = new ArrayList<>();
		
		for (Map.Entry<Long, Long> entry : perfumesId.entrySet()) {
            Perfume perfume = perfumeRepository.findById(entry.getKey()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setPerfume(perfume);
            orderItem.setAmount((perfume.getPrice() * entry.getValue()));
            orderItem.setQuantity(entry.getValue());
            orderItemList.add(orderItem);
            orderItemRepository.save(orderItem);
        }
		
		order.getOrderItems().addAll(orderItemList);
        order.setTotalPrice(validOrder.getTotalPrice());
        order.setFirstName(validOrder.getFirstName());
        order.setLastName(validOrder.getLastName());
        order.setCity(validOrder.getCity());
        order.setAddress(validOrder.getAddress());
        order.setPostIndex(validOrder.getPostIndex());
        order.setEmail(validOrder.getEmail());
        order.setPhoneNumber(validOrder.getPhoneNumber());
        orderRepository.save(order);
        
		return order;
	}

	@Override
	public List<Order> deleteOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).get();
		order.getOrderItems().forEach(orderItem -> orderItemRepository.deleteById(orderItem.getId()));
		orderRepository.delete(order);
		return orderRepository.findAllByOrderByIdAsc();
	}

	@Override
	public DataFetcher<List<Order>> getAllOrdersByQuery() {
		// TODO Auto-generated method stub
		return dataFetchingEnvironment -> orderRepository.findAllByOrderByIdAsc();
	}

	@Override
	public DataFetcher<List<Order>> getUserOrdersByEmailQuery() {
		// TODO Auto-generated method stub
		return dataFetchingEnvironment -> {
			String email = dataFetchingEnvironment.getArgument("email").toString();
			return orderRepository.findOrderByEmail(email);
		};
	}

}
