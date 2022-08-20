package com.hoanghiep.perfume.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.entity.Review;
import com.hoanghiep.perfume.enums.SearchType;
import com.hoanghiep.perfume.repositories.PerfumeProjection;

import graphql.schema.DataFetcher;

public interface PerfumeService {
	
	Perfume findPerfumeById(Long id);
	
	Page<Perfume> findAllPerfumes(Pageable pageable);
	
	List<Perfume> findPerfumesByIds(List<Long> ids);
	
	Page<Perfume> filter(List<String> perfumers, List<String> genders, List<Integer> prices, boolean sortByPrice, Pageable pageable);
	
	List<Perfume> findByPerfumerOrderByPriceDesc(String perfumer);
	
	List<Perfume> findByGenderOrderByPriceDesc(String gender);
	
	Page<PerfumeProjection> findByInputText(SearchType type, String text, Pageable pageable);
	
	Perfume savePerfume(Perfume perfume, MultipartFile multipartFile);
	
	List<Perfume> deletePerfume(Long id);
	
	List<Review> getReviewsByPerfumeId(Long perfumeId);
	
	DataFetcher<Perfume> getPerfumeByQuery();

    DataFetcher<List<Perfume>> getAllPerfumesByQuery();

    DataFetcher<List<Perfume>> getAllPerfumesByIdsQuery();
}
