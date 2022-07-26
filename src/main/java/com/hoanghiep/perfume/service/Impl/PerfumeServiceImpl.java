package com.hoanghiep.perfume.service.Impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hoanghiep.perfume.entity.Perfume;
import com.hoanghiep.perfume.entity.Review;
import com.hoanghiep.perfume.enums.SearchType;
import com.hoanghiep.perfume.repositories.PerfumeProjection;
import com.hoanghiep.perfume.repositories.PerfumeRepository;
import com.hoanghiep.perfume.repositories.ReviewRepository;
import com.hoanghiep.perfume.service.PerfumeService;

import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {
	
	private final PerfumeRepository perfumeRepository;
	
	private final ReviewRepository reviewRepository;
	
	private final AmazonS3 amazonS3client;

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;
    
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
		
		return perfumeRepository.findByPerfumerOrderByPriceDesc(perfumer);
	}

	@Override
	public List<Perfume> findByGenderOrderByPriceDesc(String gender) {
		
		return perfumeRepository.findByGenderOrderByPriceDesc(gender);
	}

	@Override
	@Transactional
	public Perfume savePerfume(Perfume perfume, MultipartFile multipartFile) {
		if(multipartFile == null) {	
			perfume.setFilename("file is null");
		} else {
			File file = new File(multipartFile.getOriginalFilename());
			try (FileOutputStream os = new FileOutputStream(file)){
				os.write(multipartFile.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String fileName = UUID.randomUUID().toString()+"."+multipartFile.getOriginalFilename();
			amazonS3client.putObject(new PutObjectRequest(bucketName, fileName, file));
            perfume.setFilename(amazonS3client.getUrl(bucketName, fileName).toString());
            file.delete();
		}
		
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

	@Override
	public List<Review> getReviewsByPerfumeId(Long perfumeId) {
		Perfume perfume = findPerfumeById(perfumeId);
		
		return perfume.getReviews();
	}

	@Override
	public Page<PerfumeProjection> findByInputText(SearchType searchType, String text, Pageable pageable) {
		if (searchType.equals(SearchType.BRAND)) {
            return perfumeRepository.findByPerfumer(text, pageable);
        } else if (searchType.equals(SearchType.PERFUME_TITLE)) {
            return perfumeRepository.findByTitle(text, pageable);
        } else {
            return perfumeRepository.findByCountry(text, pageable);
        }
	}

}
