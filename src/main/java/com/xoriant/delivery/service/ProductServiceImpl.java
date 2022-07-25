package com.xoriant.delivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoriant.delivery.constant.Constant;
import com.xoriant.delivery.dao.ProductRepo;
import com.xoriant.delivery.dto.Brand;
import com.xoriant.delivery.dto.Category;
import com.xoriant.delivery.dto.ProductDTO;
import com.xoriant.delivery.exception.ElementNotFound;
import com.xoriant.delivery.exception.InputUserException;
import com.xoriant.delivery.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private BrandCategoryServiceProxy brandCategoryServiceProxy;

	private Product product;


	@Override
	public String addNewProduct(ProductDTO productDTO, int brandId, int categoryId) {
		if (productDTO.getProductName().isEmpty() || productDTO.getProductName().isBlank()) {
			throw new InputUserException();
		}
		product = new Product();
		product.setProductId(productDTO.getProductId());

		Optional<Brand> brandResult = findByBrandId(brandId);
		if (!brandResult.isPresent()) {
			throw new ElementNotFound();
		}
		product.setBrandName(brandResult.get().getBrandName());

		Optional<Category> categoryResult = findByCategoryId(categoryId);
		if (!categoryResult.isPresent()) {
			throw new ElementNotFound();
		}
		product.setCategoryName(categoryResult.get().getCategoryName());

		product.setKeywords(productDTO.getKeywords());
		product.setPrice(productDTO.getPrice());
		product.setProductName(productDTO.getProductName());
		product.setQuantity(productDTO.getQuantity());
		productRepo.save(product);
		return Constant.NEW_PRODUCT_ADDED;
	}

	@Override
	public Optional<Brand> findByBrandId(int brandId) {
		Optional<Brand> existingBrand = brandCategoryServiceProxy.findByBrandId(brandId);
		if (!existingBrand.isPresent()) {
			throw new ElementNotFound();
		}
		return existingBrand;
	}

	@Override
	public Optional<Category> findByCategoryId(int categoryId) {
		Optional<Category> existingCategory = brandCategoryServiceProxy.findByCategoryId(categoryId);
		if (!existingCategory.isPresent()) {
			throw new ElementNotFound();
		}
		return existingCategory;
	}

	@Override
	public String addNewListsProducts(List<ProductDTO> productDTO, int brandId, int categoryId) {
		for (ProductDTO newProduct : productDTO) {
			if (newProduct.getProductName().isEmpty() || newProduct.getProductName().isBlank()) {
				throw new InputUserException();
			}
		}

		for (ProductDTO newProduct : productDTO) {
			product = new Product();
			product.setProductId(newProduct.getProductId());

			Optional<Brand> brandResult = findByBrandId(brandId);
			if (!brandResult.isPresent()) {
				throw new ElementNotFound();
			}
			product.setBrandName(brandResult.get().getBrandName());

			Optional<Category> categoryResult = findByCategoryId(categoryId);
			if (!categoryResult.isPresent()) {
				throw new ElementNotFound();
			}
			product.setCategoryName(categoryResult.get().getCategoryName());

			product.setKeywords(newProduct.getKeywords());
			product.setPrice(newProduct.getPrice());
			product.setProductName(newProduct.getProductName());
			product.setQuantity(newProduct.getQuantity());
			productRepo.save(product);
		}

		return Constant.NEW_LISTS_OF_PRODUCT_ADDED;
	}

	@Override
	public String updateProduct(ProductDTO productDTO, int brandId, int categoryId) {
		Optional<Product> existingProduct = productRepo.findById(productDTO.getProductId());
		if (!existingProduct.isPresent()) {
			throw new ElementNotFound();
		}
		if (productDTO.getProductName().isEmpty() || productDTO.getProductName().isBlank()) {
			throw new InputUserException();
		}
		Product updateProduct = productRepo.findById(productDTO.getProductId()).orElse(null);
		updateProduct.setProductId(productDTO.getProductId());

		Optional<Brand> brandResult = findByBrandId(brandId);
		if (!brandResult.isPresent()) {
			throw new ElementNotFound();
		}
		updateProduct.setBrandName(brandResult.get().getBrandName());

		Optional<Category> categoryResult = findByCategoryId(categoryId);
		if (!categoryResult.isPresent()) {
			throw new ElementNotFound();
		}
		updateProduct.setCategoryName(categoryResult.get().getCategoryName());

		updateProduct.setKeywords(productDTO.getKeywords());
		updateProduct.setPrice(productDTO.getPrice());
		updateProduct.setProductName(productDTO.getProductName());
		updateProduct.setQuantity(productDTO.getQuantity());
		productRepo.save(updateProduct);

		return Constant.UPDATE_EXISTING_PRODUCT;
	}

	@Override
	public String updateListsProduct(List<ProductDTO> productDTO, int brandId, int categoryId) {

		for (ProductDTO newProduct : productDTO) {
			Optional<Product> existingProduct = productRepo.findById(newProduct.getProductId());
			if (!existingProduct.isPresent()) {
				throw new ElementNotFound();
			}
			if (newProduct.getProductName().isEmpty() || newProduct.getProductName().isBlank()) {
				throw new InputUserException();
			}
		}

		for (ProductDTO newProduct : productDTO) {
			Product updateProduct = productRepo.findById(newProduct.getProductId()).orElse(null);
			updateProduct.setProductId(newProduct.getProductId());

			Optional<Brand> brandResult = findByBrandId(brandId);
			if (!brandResult.isPresent()) {
				throw new ElementNotFound();
			}
			updateProduct.setBrandName(brandResult.get().getBrandName());

			Optional<Category> categoryResult = findByCategoryId(categoryId);
			if (!categoryResult.isPresent()) {
				throw new ElementNotFound();
			}
			updateProduct.setCategoryName(categoryResult.get().getCategoryName());

			updateProduct.setKeywords(newProduct.getKeywords());
			updateProduct.setPrice(newProduct.getPrice());
			updateProduct.setProductName(newProduct.getProductName());
			updateProduct.setQuantity(newProduct.getQuantity());
			productRepo.save(updateProduct);

		}
		return Constant.UPDATE_LISTS_OF_PRODUCTS;
	}

	@Override
	public void deleteProduct(int productId) {
		Optional<Product> existingProduct = productRepo.findById(productId);
		if (!existingProduct.isPresent()) {
			throw new ElementNotFound();
		}
		productRepo.deleteById(productId);
	}

	@Override
	public Optional<Product> findByProductName(String productName) {
		Optional<Product> existingProduct = productRepo.findByProductName(productName);
		if (!existingProduct.isPresent()) {
			throw new ElementNotFound();
		}
		return existingProduct;
	}

	@Override
	public Optional<Product> findByProductId(int productId) {
		Optional<Product> existingProduct = productRepo.findById(productId);
		if (!existingProduct.isPresent()) {
			throw new ElementNotFound();
		}
		return existingProduct;
	}

	@Override
	public List<Product> findAllProducts() {
		List<Product> existingProduct = productRepo.findAll();
		if (existingProduct.isEmpty()) {
			throw new ElementNotFound();
		}
		return existingProduct;
	}

	@Override
	public List<Product> findByBrandName(String brandName) {
		List<Product> existingProduct = productRepo.findByBrandName(brandName);
		if (existingProduct.isEmpty()) {
			throw new ElementNotFound();
		}
		return existingProduct;
	}

	@Override
	public List<Product> findByCategoryName(String categoryName) {
		List<Product> existingProduct = productRepo.findByCategoryName(categoryName);
		if (existingProduct.isEmpty()) {
			throw new ElementNotFound();
		}
		return existingProduct;
	}

	@Override
	public List<Product> findByPriceInBetween(double minPrice, double maxPrice) {
		List<Product> existingProduct = productRepo.findByPriceIsBetween(minPrice, maxPrice);
		if (existingProduct.isEmpty()) {
			throw new ElementNotFound();
		}
		return existingProduct;
	}

	@Override
	public List<Product> findByPriceGreaterThan(double minPrice) {
		List<Product> existingProduct = productRepo.findByPriceGreaterThan(minPrice);
		if (existingProduct.isEmpty()) {
			throw new ElementNotFound();
		}
		return existingProduct;
	}

	@Override
	public List<Product> findByPriceLessThan(double minPrice) {
		List<Product> existingProduct = productRepo.findByPriceLessThan(minPrice);
		if (existingProduct.isEmpty()) {
			throw new ElementNotFound();
		}
		return existingProduct;
	}

}
