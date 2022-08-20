package com.hoanghiep.perfume.repositories;

import org.springframework.beans.factory.annotation.Value;

public interface PerfumeProjection {
	Long getId();
    String getTitle();
    String getPerfumer();
    Integer getPrice();
    String getFilename();
    Double getRating();
    
    @Value("#{target.reviews.size()}")
    Integer getReviewsCount();

    void setPerfumer(String perfumer);
    void setGender(String perfumeGender);
    void setPrice(Integer price);
}
