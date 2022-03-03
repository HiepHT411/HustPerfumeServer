package com.hoanghiep.perfume.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReviewRequest {
	
	private Long perfumeId;
	
	@NotBlank(message = "author can not be null")
	private String author;
	
	@NotBlank(message = "message can not be null")
	private String message;
	
	@Min(value = 1, message = "Choose rating from 1 to 5")
	private String rating;
}
