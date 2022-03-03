package com.hoanghiep.perfume.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchWithFilterRequest {
	
	private List<String> perfumers;
	private List<String> genders;
	private List<Integer> prices;
	private String perfumer;
	private String gender;
	private boolean sortByPrice;
}
