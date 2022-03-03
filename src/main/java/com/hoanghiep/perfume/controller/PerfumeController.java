package com.hoanghiep.perfume.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoanghiep.perfume.dto.SearchWithFilterRequest;
import com.hoanghiep.perfume.entity.Perfume;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/hust/perfumes")
public class PerfumeController {
	
	@GetMapping
	public ResponseEntity<List<Perfume>> getAllPerfumes(){
		
		return null;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Perfume> getPerfumeById(@PathVariable("id") Long id){
		
		return null;
	}
	
	@PostMapping("/ids")
	public ResponseEntity<List<Perfume>> getSomePerfumesByIdList(@RequestBody List<String> ids){
		
		return null;
	}
	
	//Tao 1 class SearchRequest cho Perfume, tao 1 PerfumeResponse (option)
	@PostMapping("/search")
	public ResponseEntity<List<Perfume>> getPerfumesWithFilter(@RequestBody SearchWithFilterRequest filter){
		return null;
		
	}
	
	@PostMapping("/search/gender")
	public ResponseEntity<List<Perfume>> getPerfumesByGender(@RequestBody SearchWithFilterRequest filter){
		return null;
		
	}
	
	@PostMapping("/search/perfumer")
	public ResponseEntity<List<Perfume>> getPerfumesByPerfumer(@RequestBody SearchWithFilterRequest filter){
		return null;
		
	}
	
	//ung dung graphql ...
	
	
	
	
	
	
}
