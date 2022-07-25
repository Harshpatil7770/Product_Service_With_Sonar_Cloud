package com.xoriant.delivery.service;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.xoriant.delivery.dto.Brand;
import com.xoriant.delivery.dto.Category;

@FeignClient(name = "brand-category-service", url = "http://localhost:9090")
public interface BrandCategoryServiceProxy {

	@GetMapping("/api/brands/find-brand/{brandId}")
	public Optional<Brand> findByBrandId(@PathVariable int brandId);

	@GetMapping("/api/categories/find-category/{categoryId}")
	public Optional<Category> findByCategoryId(@PathVariable int categoryId);
}
