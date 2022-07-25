package com.xoriant.delivery.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xoriant.delivery.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

	Optional<Product> findByProductName(String productName);

	List<Product> findByBrandName(String brandName);

	List<Product> findByCategoryName(String categoryName);

	List<Product> findByPriceIsBetween(double minPrice, double maxPrice);

	List<Product> findByPriceGreaterThan(double minPrice);

	List<Product> findByPriceLessThan(double minPrice);

}
