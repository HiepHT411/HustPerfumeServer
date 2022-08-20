package com.hoanghiep.perfume.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hoanghiep.perfume.entity.Review;

import lombok.Data;

@Data
public class PerfumeResponse {
	
	private Long id;
    private String title;
    private String perfumer;
    private Integer year;
    private String country;
    private String gender;
    private String fragranceTopNotes;
    private String fragranceMiddleNotes;
    private String fragranceBaseNotes;
    private String description;
    private Integer price;
    private String volume;
    private String type;
    private Double rating;
    private List<Review> reviews;
    private String filename;
    //private MultipartFile file;
}
