package com.hoanghiep.perfume.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoanghiep.perfume.entity.Perfume;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {
	
	List<Perfume> findByIdIn(List<Long> ids);
	
	List<Perfume> findAllByOrderByIdAsc();
	
	List<Perfume> findByPerfumerIn(List<String> perfumers);
	
	List<Perfume> findByGenderIn(List<String> genders);
	
	List<Perfume> findByPriceBetween(Integer startPrice, Integer endPrice);
	
	List<Perfume> findByPerfumerByOrderByPriceDesc(String perfumer);
	
	List<Perfume> findByGenderByOrderByPriceDesc(String gender);
	
	
	
}
