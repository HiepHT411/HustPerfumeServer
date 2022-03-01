package com.hoanghiep.perfume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoanghiep.perfume.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
