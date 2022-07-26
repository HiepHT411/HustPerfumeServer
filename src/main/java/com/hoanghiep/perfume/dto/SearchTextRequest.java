package com.hoanghiep.perfume.dto;

import com.hoanghiep.perfume.enums.SearchType;

import lombok.Data;

@Data
public class SearchTextRequest {
	private SearchType type;
	private String text;
}
