package com.hoanghiep.perfume.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hoanghiep.perfume.dto.HeaderResponse;
import com.hoanghiep.perfume.dto.PerfumeRequest;
import com.hoanghiep.perfume.dto.PerfumeResponse;
import com.hoanghiep.perfume.dto.ReviewResponse;
import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.entity.Review;
import com.hoanghiep.perfume.enums.SearchType;
import com.hoanghiep.perfume.repositories.PerfumeProjection;
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
	
	public HeaderResponse<PerfumeResponse> getAllPerfumes(Pageable pageable){
		Page<Perfume> page = perfumeService.findAllPerfumes(pageable);
		//return convertListOfEntityToResponse(perfumeService.findAllPerfumes(pageable));
		return getHeaderResponse(page.getContent(), page.getTotalPages(), page.getTotalElements(), PerfumeResponse.class);
	}
	
	public PerfumeResponse getPerfumeById(Long id) {
		return convertEntityToResponse(perfumeService.findPerfumeById(id));
	}
	
	public List<PerfumeResponse> getListOfPerfumeByIds(List<Long> ids){
		return convertListOfEntityToResponse(perfumeService.findPerfumesByIds(ids));
	}
	
	public HeaderResponse<PerfumeResponse> getPerfumesWithFilter(List<String> perfumers, List<String> genders,
														List<Integer> prices, boolean sortByPrice, Pageable pageable){
		Page<Perfume> perfumes = perfumeService.filter(perfumers, genders, prices, sortByPrice, pageable);
	
		return getHeaderResponse(perfumes.getContent(), perfumes.getTotalPages(), perfumes.getTotalElements(), PerfumeResponse.class);
	}
	
	public List<PerfumeResponse> getPerfumesByGender(String gender){
		return convertListOfEntityToResponse(perfumeService.findByGenderOrderByPriceDesc(gender));
	}
	
	public List<PerfumeResponse> getPerfumesByPerfumer(String perfumer){
		return convertListOfEntityToResponse(perfumeService.findByPerfumerOrderByPriceDesc(perfumer));
	}
	
	
	
	
	<T, S> HeaderResponse<S> getHeaderResponse(List<T> list, Integer totalPages, Long totalElements, Class<S> type) {
        //List<S> orderResponses = convertToResponseList(list, type);
		List<S> res = list.stream().map(item -> modelMapper.map(item, type)).collect(Collectors.toList());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("page-total-count", String.valueOf(totalPages));
        responseHeaders.add("page-total-elements", String.valueOf(totalElements));
        return new HeaderResponse<S>(res, responseHeaders);
    }
	public HeaderResponse<PerfumeResponse> findPerfumesByInputText(SearchType searchType, String text, Pageable page){
		Page<PerfumeProjection> perfumes = perfumeService.findByInputText(searchType, text, page);
        return getHeaderResponse(perfumes.getContent(), perfumes.getTotalPages(), perfumes.getTotalElements(), PerfumeResponse.class);
        
	}
	
	public PerfumeResponse savePerfume(PerfumeRequest perfume, MultipartFile file) {
		return convertEntityToResponse(perfumeService.savePerfume(convertRequestToEntity(perfume), file));
	}
	
	public List<PerfumeResponse> deletePerfume(Long id){
		return convertListOfEntityToResponse(perfumeService.deletePerfume(id));
	}

	
	
	
	
//	<T, S> S convertToResponse(T data, Class<S> type) {
//        return modelMapper.map(data, type);
//    }
//
//    <T, S> List<S> convertToResponseList(List<T> lists, Class<S> type) {
//        return lists.stream()
//                .map(list -> convertToResponse(list, type))
//                .collect(Collectors.toList());
//    }
    
	public List<ReviewResponse> getReviewsByPerfumeId(Long perfumeId) {
		List<Review> list = perfumeService.getReviewsByPerfumeId(perfumeId);
		List<ReviewResponse> res = list.stream().map(item -> modelMapper.map(item, ReviewResponse.class)).collect(Collectors.toList());
					//list.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
		return res;
	}
}
