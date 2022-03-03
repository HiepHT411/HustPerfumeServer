package com.hoanghiep.perfume.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class PerfumeRequest {
	
	private Long id;
	
	@NotBlank(message = "title can not be null")
    @Length(max = 255)
    private String title;
	
	@NotBlank(message = "perfumer can not be null")
    @Length(max = 255)
    private String perfumer;
	
	@NotNull(message = "year can not be null")
    private Integer year;
	
	@NotBlank(message = "country can not be null")
    @Length(max = 255)
    private String country;
	
	@NotBlank(message = "gender can not be null")
    @Length(max = 255)
    private String gender;
	
	@NotBlank(message = "fragrancce top notes can not be null")
    @Length(max = 255)
    private String fragranceTopNotes;
	
	@NotBlank(message = "fragnance middle notes can not be null")
    @Length(max = 255)
    private String fragranceMiddleNotes;
	
	@NotBlank(message = "fragnance base notes can not be null")
    @Length(max = 255)
    private String fragranceBaseNotes;
	
	@NotNull(message = "price can not be null")
    private Integer price;
	
	@NotBlank(message = "volume can not be null")
    @Length(max = 255)
    private String volume;
	
	@NotBlank(message = "type can not be null")
    @Length(max = 255)
    private String type;
    
	private String filename;
}
