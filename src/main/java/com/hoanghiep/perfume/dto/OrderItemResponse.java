package com.hoanghiep.perfume.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
	private Long id;
    private Long amount;
    private Long quantity;
    private PerfumeResponse perfume;
}
