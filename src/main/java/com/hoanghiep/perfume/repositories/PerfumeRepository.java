package com.hoanghiep.perfume.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoanghiep.perfume.entity.Perfume;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {
	
	List<Perfume> findByIdIn(List<Long> ids);
	
	Page<Perfume> findAllByOrderByIdAsc(Pageable pageable);
	
	List<Perfume> findByPerfumerIn(List<String> perfumers);
	
	List<Perfume> findByGenderIn(List<String> genders);
	
	List<Perfume> findByPriceBetween(Integer startPrice, Integer endPrice);
	
	List<Perfume> findByPerfumerOrderByPriceDesc(String perfumer);
	
	List<Perfume> findByGenderOrderByPriceDesc(String gender);
	
	@Query("SELECT perfume FROM Perfume perfume " +
            "WHERE (coalesce(:perfumers, null) IS NULL OR perfume.perfumer IN :perfumers) " +
            "AND (coalesce(:genders, null) IS NULL OR perfume.gender IN :genders) " +
            "AND (coalesce(:priceStart, null) IS NULL OR perfume.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY CASE WHEN :sortByPrice = true THEN perfume.price ELSE -perfume.price END ASC")
    Page<Perfume> findPerfumesByFilterParams(
            List<String> perfumers, 
            List<String> genders, 
            Integer priceStart, 
            Integer priceEnd, 
            boolean sortByPrice,
            Pageable pageable);
	
	@Query("SELECT perfume FROM Perfume perfume " +
            "WHERE UPPER(perfume.perfumer) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY perfume.price DESC")
    Page<PerfumeProjection> findByPerfumer(String text, Pageable pageable);

    @Query("SELECT perfume FROM Perfume perfume " +
            "WHERE UPPER(perfume.title) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY perfume.price DESC")
    Page<PerfumeProjection> findByTitle(String text, Pageable pageable);

    @Query("SELECT perfume FROM Perfume perfume " +
            "WHERE UPPER(perfume.country) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY perfume.price DESC")
    Page<PerfumeProjection> findByCountry(String text, Pageable pageable);
	
}
