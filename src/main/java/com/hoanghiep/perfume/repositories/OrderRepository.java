package com.hoanghiep.perfume.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoanghiep.perfume.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findAllByOrderByIdAsc();
	
	List<Order> findOrderByEmail(String email);
}
