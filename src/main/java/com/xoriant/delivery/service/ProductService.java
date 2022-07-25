package com.xoriant.delivery.service;

import java.util.List;
import java.util.Optional;

import com.xoriant.delivery.dto.Brand;
import com.xoriant.delivery.dto.Category;
import com.xoriant.delivery.dto.ProductDTO;
import com.xoriant.delivery.model.Product;

public interface ProductService {

	String addNewProduct(ProductDTO productDTO, int brandId, int categoryId);

	Optional<Brand> findByBrandId(int brandId);

	Optional<Category> findByCategoryId(int categoryId);

	String addNewListsProducts(List<ProductDTO> productDTO, int brandId, int categoryId);

	String updateProduct(ProductDTO productDTO, int brandId, int categoryId);

	String updateListsProduct(List<ProductDTO> productDTO, int brandId, int categoryId);

	void deleteProduct(int productId);

	Optional<Product> findByProductName(String productName);

	Optional<Product> findByProductId(int productId);

	List<Product> findAllProducts();

	List<Product> findByBrandName(String brandName);

	List<Product> findByCategoryName(String categoryName);

	List<Product> findByPriceInBetween(double minPrice, double maxPrice);

	List<Product> findByPriceGreaterThan(double minPrice);

	List<Product> findByPriceLessThan(double minPrice);

}
