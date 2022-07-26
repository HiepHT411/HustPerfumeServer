package com.hoanghiep.perfume.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewResponse {
	private Long id;
    private String author;
    private String message;
    private Integer rating;
    private LocalDate date;
}
