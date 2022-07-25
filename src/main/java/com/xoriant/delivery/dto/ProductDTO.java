package com.xoriant.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private int categoryId;

	private String categoryName;
	
	private int brandId;

	private String brandName;
	
	private int productId;

	private String productName;

	private double price;

	private int quantity;

	private String keywords;
	
}
