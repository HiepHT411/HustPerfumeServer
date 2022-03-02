package com.hoanghiep.perfume.service.Impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.repository.PerfumeRepository;
import com.hoanghiep.perfume.repository.ReviewRepository;
import com.hoanghiep.perfume.service.PerfumeService;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {
	
	private final PerfumeRepository perfumeRepository;
	
	private final ReviewRepository reviewRepository;
	
	@Override
	public Perfume findPerfumeById(Long id) {

		return perfumeRepository.findById(id).get();
	}

	@Override
	public List<Perfume> findAllPerfumes() {
  
		return perfumeRepository.findAll();
	}

	@Override
	public List<Perfume> findPerfumesByIds(List<Long> ids) {

		return perfumeRepository.findByIdIn(ids);
	}

	
	//filter voi cac field perfumer, gender va price
	@Override
	public List<Perfume> filter(List<String> perfumers, List<String> genders, List<Integer> prices,
			boolean sortByPrice) {
		
		List<Perfume> filterList = perfumeRepository.findAll();

		
		if(!perfumers.isEmpty() || !genders.isEmpty() || !prices.isEmpty()) {
			if(!perfumers.isEmpty()) {
				List<Perfume> matchList = new ArrayList<>();
				
				for(String perfumer : perfumers) {
					matchList.addAll(filterList.stream()
										.filter(perfume -> perfume.getPerfumer().equals(perfumer))
										.collect(Collectors.toList()));
				}
				filterList = matchList;
			} else {
                filterList.addAll(perfumeRepository.findByPerfumerIn(perfumers));
            }
			
			if(!genders.isEmpty()) {
				List<Perfume> matchList = new ArrayList<>();
				
				for(String gender : genders) {
					matchList.addAll(filterList.stream()
										.filter(perfume -> perfume.getGender().equals(gender))
										.collect(Collectors.toList()));
				}
				filterList = matchList;
			} else {
				filterList.addAll(perfumeRepository.findByGenderIn(genders));
			}
			
			
			//lay cac san pham trong khoang gia
			if(!prices.isEmpty()) {
				List<Perfume> matchList = new ArrayList<>();
				
				for(Perfume perfume : filterList) {
					if ((perfume.getPrice() >= prices.get(0))&&(perfume.getPrice() <= prices.get(1))) {
						matchList.add(perfume);
					}
				}
				filterList = matchList;
			} 
//			else {
//				filterList = perfumeRepository.findByPriceBetween(prices.get(0), prices.get(1));
//			}
			
		}
		else {
			filterList = perfumeRepository.findAllByOrderByIdAsc();
		}
		
		if(sortByPrice) {
			filterList.sort(Comparator.comparing(Perfume::getPrice));
		}
		else {
			filterList.sort((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
		}
		
		return filterList;
	}

	@Override
	public List<Perfume> findByPerfumerOrderByPriceDesc(String perfumer) {
		
		return perfumeRepository.findByPerfumerByOrderByPriceDesc(perfumer);
	}

	@Override
	public List<Perfume> findByGenderOrderByPriceDesc(String gender) {
		
		return perfumeRepository.findByGenderByOrderByPriceDesc(gender);
	}

	@Override
	public Perfume savePerfume(Perfume perfume, MultipartFile multipartFile) {
		
		
		return perfumeRepository.save(perfume);
	}

	@Override
	@Transactional
	public List<Perfume> deletePerfume(Long id) {
		
		Perfume perfume = perfumeRepository.findById(id).get();
		perfume.getReviews().forEach(review -> reviewRepository.delete(review));
		perfumeRepository.delete(perfume);
		
		return perfumeRepository.findAllByOrderByIdAsc();
	}

	//graphQL
	@Override
	public DataFetcher<Perfume> getPerfumeByQuery() {
		
		return dataFetchingEnvironment -> {
			Long id = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
			return perfumeRepository.findById(id).get();
		};
	}

	@Override
	public DataFetcher<List<Perfume>> getAllPerfumesByQuery() {
		
		return dataFetchingEnvironment -> perfumeRepository.findAllByOrderByIdAsc();
	}

	@Override
	public DataFetcher<List<Perfume>> getAllPerfumesByIdsQuery() {
		
		return dataFetchingEnvironment -> {
			
			List<String> idsInString = dataFetchingEnvironment.getArgument("ids");
			List<Long> ids = idsInString.stream().map(Long::parseLong)
										.collect(Collectors.toList());
			return perfumeRepository.findByIdIn(ids);
		};
	}

}
