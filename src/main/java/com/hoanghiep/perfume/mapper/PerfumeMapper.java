package com.hoanghiep.perfume.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hoanghiep.perfume.dto.PerfumeRequest;
import com.hoanghiep.perfume.dto.PerfumeResponse;
import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.service.PerfumeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PerfumeMapper {

	private final ModelMapper modelMapper;
	
//	@Autowired
//	public PerfumeMapper(ModelMapper modelMapper) {
//		this.modelMapper = modelMapper;
//	}
	
	private final PerfumeService perfumeService;
	
	private Perfume convertRequestToEntity(PerfumeRequest request) {
		return modelMapper.map(request, Perfume.class);
	}
	
	PerfumeResponse convertEntityToResponse(Perfume perfume) {
		return modelMapper.map(perfume, PerfumeResponse.class);
	}
	
	List<PerfumeResponse> convertListOfEntityToResponse(List<Perfume> perfumes){
		return perfumes.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
	}
	
	public List<PerfumeResponse> getAllPerfumes(){
		return convertListOfEntityToResponse(perfumeService.findAllPerfumes());
	}
	
	public PerfumeResponse getPerfumeById(Long id) {
		return convertEntityToResponse(perfumeService.findPerfumeById(id));
	}
	
	public List<PerfumeResponse> getListOfPerfumeByIds(List<Long> ids){
		return convertListOfEntityToResponse(perfumeService.findPerfumesByIds(ids));
	}
	
	public List<PerfumeResponse> getPerfumesWithFilter(List<String> perfumers, List<String> genders,
														List<Integer> prices, boolean sortByPrice){
		return convertListOfEntityToResponse(perfumeService.filter(perfumers, genders, prices, sortByPrice));
	}
	
	public List<PerfumeResponse> getPerfumesByGender(String gender){
		return convertListOfEntityToResponse(perfumeService.findByGenderOrderByPriceDesc(gender));
	}
	
	public List<PerfumeResponse> getPerfumesByPerfumer(String perfumer){
		return convertListOfEntityToResponse(perfumeService.findByGenderOrderByPriceDesc(perfumer));
	}
	
	public PerfumeResponse savePerfume(PerfumeRequest perfume, MultipartFile file) {
		return convertEntityToResponse(perfumeService.savePerfume(convertRequestToEntity(perfume), file));
	}
	
	List<PerfumeResponse> deletePerfume(Long id){
		return convertListOfEntityToResponse(perfumeService.deletePerfume(id));
	}
}
