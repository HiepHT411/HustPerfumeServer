package com.hoanghiep.perfume.service;

import java.util.List;
import java.util.Map;

import com.hoanghiep.perfume.entity.Order;

import graphql.schema.DataFetcher;

public interface OrderService {
	
	List<Order> findAll();

    List<Order> findOrderByEmail(String email);

    Order postOrder(Order validOrder, Map<Long, Long> perfumesId);

    List<Order> deleteOrder(Long orderId);

    DataFetcher<List<Order>> getAllOrdersByQuery();

    DataFetcher<List<Order>> getUserOrdersByEmailQuery();
}
