package com.hoanghiep.perfume.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReviewRequest {
	
	private Long perfumeId;
	
	@NotBlank(message = "author can not be null")
	private String author;
	
	@NotBlank(message = "message can not be null")
	private String message;
	
	@NotNull(message = "Choose rating from 1 to 5")
	@Min(value =1)
	private Integer rating;
}
