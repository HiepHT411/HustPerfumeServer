package com.hoanghiep.perfume.controllers;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoanghiep.perfume.dto.HeaderResponse;
import com.hoanghiep.perfume.dto.PerfumeResponse;
import com.hoanghiep.perfume.dto.ReviewResponse;
import com.hoanghiep.perfume.dto.SearchTextRequest;
import com.hoanghiep.perfume.dto.SearchWithFilterRequest;
import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.mappers.PerfumeMapper;

import lombok.RequiredArgsConstructor;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/hust/perfumes")
@RequiredArgsConstructor
public class PerfumeController {
	
	private final PerfumeMapper perfumeMapper;
	
	@GetMapping
	public ResponseEntity<List<PerfumeResponse>> getAllPerfumes(@PageableDefault(size = 15) Pageable pageable){
		HeaderResponse<PerfumeResponse> res = perfumeMapper.getAllPerfumes(pageable);
		
		return ResponseEntity.ok().headers(res.getHeaders()).body(res.getItems());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PerfumeResponse> getPerfumeById(@PathVariable("id") Long id){
		
		return ResponseEntity.ok(perfumeMapper.getPerfumeById(id));
	}
	
	@PostMapping("/ids")
	public ResponseEntity<List<PerfumeResponse>> getSomePerfumesByIdList(@RequestBody List<Long> ids){
		
		return ResponseEntity.ok(perfumeMapper.getListOfPerfumeByIds(ids));
	}
	
	@GetMapping("/reviews/{perfumeId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByPerfumeId(@PathVariable Long perfumeId) {
        return ResponseEntity.ok(perfumeMapper.getReviewsByPerfumeId(perfumeId));
    }
	
	@PostMapping("/search")
	public ResponseEntity<List<PerfumeResponse>> searchPerfumesWithFilter(@RequestBody SearchWithFilterRequest filter, @PageableDefault(size = 15) Pageable pageable){
		
		HeaderResponse<PerfumeResponse> res = perfumeMapper.getPerfumesWithFilter(filter.getPerfumers(), filter.getGenders(), filter.getPrices(), filter.isSortByPrice(), pageable);
		
		return ResponseEntity.ok().headers(res.getHeaders()).body(res.getItems());
	}
	
	@PostMapping("/search/gender")
	public ResponseEntity<List<PerfumeResponse>> searchPerfumesByGender(@RequestBody SearchWithFilterRequest filter){
		
		return ResponseEntity.ok(perfumeMapper.getPerfumesByGender(filter.getGender()));
		
	}
	
	@PostMapping("/search/perfumer")
	public ResponseEntity<List<PerfumeResponse>> searchPerfumesByPerfumer(@RequestBody SearchWithFilterRequest filter){
		
		return ResponseEntity.ok(perfumeMapper.getPerfumesByPerfumer(filter.getPerfumer()));
		
	}
	
	@PostMapping("/search/text")
	public ResponseEntity<List<PerfumeResponse>> searchPerfumesByText(@RequestBody SearchTextRequest searchType, @PageableDefault(size = 15) Pageable page){
		HeaderResponse<PerfumeResponse> res =  perfumeMapper.findPerfumesByInputText(searchType.getType(), searchType.getText(), page);
		return ResponseEntity.ok().headers(res.getHeaders()).body(res.getItems());
	}
	//ung dung graphql ...
	
	
	
	
	
	
}
