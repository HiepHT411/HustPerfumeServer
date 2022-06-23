package com.hoanghiep.perfume.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoanghiep.perfume.dto.PerfumeResponse;
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
	public ResponseEntity<List<PerfumeResponse>> getAllPerfumes(){
		
		return ResponseEntity.ok(perfumeMapper.getAllPerfumes());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PerfumeResponse> getPerfumeById(@PathVariable("id") Long id){
		
		return ResponseEntity.ok(perfumeMapper.getPerfumeById(id));
	}
	
	@PostMapping("/ids")
	public ResponseEntity<List<PerfumeResponse>> getSomePerfumesByIdList(@RequestBody List<Long> ids){
		
		return ResponseEntity.ok(perfumeMapper.getListOfPerfumeByIds(ids));
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<PerfumeResponse>> searchPerfumesWithFilter(@RequestBody SearchWithFilterRequest filter){
		
		return ResponseEntity.ok(perfumeMapper.getPerfumesWithFilter(filter.getPerfumers(), filter.getGenders(), filter.getPrices(), filter.isSortByPrice()));
		
	}
	
	@PostMapping("/search/gender")
	public ResponseEntity<List<PerfumeResponse>> searchPerfumesByGender(@RequestBody SearchWithFilterRequest filter){
		
		return ResponseEntity.ok(perfumeMapper.getPerfumesByGender(filter.getGender()));
		
	}
	
	@PostMapping("/search/perfumer")
	public ResponseEntity<List<PerfumeResponse>> searchPerfumesByPerfumer(@RequestBody SearchWithFilterRequest filter){
		
		return ResponseEntity.ok(perfumeMapper.getPerfumesByPerfumer(filter.getPerfumer()));
		
	}
	
	//ung dung graphql ...
	
	
	
	
	
	
}
