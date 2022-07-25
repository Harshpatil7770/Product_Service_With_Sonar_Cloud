package com.xoriant.delivery.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.xoriant.delivery.constant.Constant;
import com.xoriant.delivery.dto.Brand;
import com.xoriant.delivery.dto.Category;
import com.xoriant.delivery.dto.ProductDTO;
import com.xoriant.delivery.model.Product;
import com.xoriant.delivery.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductResource productResource;

	private ProductDTO productDTO;
	private ProductDTO productDTO1;

	private Product product;
	private Product product1;

	private Brand brand;

	private Category category;

	static final int BRAND_ID = 101;
	static final int CATEGORY_ID = 501;

	@BeforeEach
	void setUp() {
		productDTO = new ProductDTO(601, "Mobile", 501, "Oppo", 101, "Oppo F1f", 15999, 120, "selfi expert");
		productDTO1 = new ProductDTO(601, "Mobile", 501, "Oppo", 102, "Oppo F17", 17999, 125, "5g");

		product = new Product(productDTO.getProductId(), productDTO.getProductName(), productDTO.getPrice(),
				productDTO.getQuantity(), productDTO.getKeywords(), productDTO.getBrandName(),
				productDTO.getCategoryName());

		product1 = new Product(productDTO.getProductId(), productDTO.getProductName(), productDTO.getPrice(),
				productDTO.getQuantity(), productDTO.getKeywords(), productDTO.getBrandName(),
				productDTO.getCategoryName());

		brand = new Brand(productDTO.getBrandId(), productDTO.getBrandName());
		category = new Category(productDTO.getCategoryId(), productDTO.getCategoryName());
	}

	@Test
	void addNewProduct() {
		when(productService.addNewProduct(productDTO, BRAND_ID, CATEGORY_ID)).thenReturn(Constant.NEW_PRODUCT_ADDED);
		String result = productService.addNewProduct(productDTO, BRAND_ID, CATEGORY_ID);
		assertEquals(new ResponseEntity<>(result, HttpStatus.OK),
				productResource.addNewProduct(productDTO, BRAND_ID, CATEGORY_ID));
	}

	@Test
	void addNewListsProducts() {
		List<ProductDTO> productLists = new ArrayList<>();
		productLists.add(productDTO);
		productLists.add(productDTO1);
		when(productService.addNewListsProducts(productLists, BRAND_ID, CATEGORY_ID))
				.thenReturn(Constant.NEW_LISTS_OF_PRODUCT_ADDED);
		String result = productService.addNewListsProducts(productLists, BRAND_ID, CATEGORY_ID);
		assertEquals(new ResponseEntity<>(result, HttpStatus.OK),
				productResource.addNewListsProducts(productLists, BRAND_ID, CATEGORY_ID));
	}

	@Test
	void updateProduct() {
		when(productService.updateProduct(productDTO, BRAND_ID, CATEGORY_ID))
				.thenReturn(Constant.UPDATE_EXISTING_PRODUCT);
		String result = productService.updateProduct(productDTO, BRAND_ID, CATEGORY_ID);
		assertEquals(new ResponseEntity<>(result, HttpStatus.OK),
				productResource.updateProduct(productDTO, BRAND_ID, CATEGORY_ID));
	}

	@Test
	void updateListsProduct() {
		List<ProductDTO> productLists = new ArrayList<>();
		productLists.add(productDTO);
		productLists.add(productDTO1);
		when(productService.updateListsProduct(productLists, BRAND_ID, CATEGORY_ID))
				.thenReturn(Constant.UPDATE_LISTS_OF_PRODUCTS);
		String result = productService.updateListsProduct(productLists, BRAND_ID, CATEGORY_ID);
		assertEquals(new ResponseEntity<>(result, HttpStatus.OK),
				productResource.updateListsProduct(productLists, BRAND_ID, CATEGORY_ID));
	}

	@Test
	void deleteProduct() {
		int productId = 101;
		productService.deleteProduct(productId);
		verify(productService, times(1)).deleteProduct(productId);
	}

	@Test
	void findByProductName() {
		String productName = "Oppo F1f";
		Optional<Product> existingProduct = Optional.of(product);
		when(productService.findByProductName(productName)).thenReturn(existingProduct);
		assertEquals(existingProduct, productResource.findByProductName(productName));
	}

	@Test
	void findByProductId() {
		int productId = 101;
		Optional<Product> existingProduct = Optional.of(product);
		when(productService.findByProductId(productId)).thenReturn(existingProduct);
		assertEquals(existingProduct, productResource.findByProductId(productId));
	}

	@Test
	void findAllProducts() {
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);

		when(productService.findAllProducts()).thenReturn(productLists);
		assertEquals(productLists, productResource.findAllProducts());
	}

	@Test
	void findByBrandName() {
		String brandName = "Oppo";
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		when(productService.findByBrandName(brandName)).thenReturn(productLists);
		assertEquals(productLists, productResource.findByBrandName(brandName));
	}

	@Test
	void findByCategoryName() {
		String categoryName = "Oppo";
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		when(productService.findByCategoryName(categoryName)).thenReturn(productLists);
		assertEquals(productLists, productResource.findByCategoryName(categoryName));
	}

	@Test
	void findByPriceInBetween() {
		double minPrice = 10000;
		double maxPrice = 19000;
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		when(productService.findByPriceInBetween(minPrice, maxPrice)).thenReturn(productLists);
		assertEquals(productLists, productResource.findByPriceInBetween(minPrice, maxPrice));
	}

	@Test
	void findByPriceGreaterThan() {

		double minPrice = 10000;
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		when(productService.findByPriceGreaterThan(minPrice)).thenReturn(productLists);
		assertEquals(productLists, productResource.findByPriceGreaterThan(minPrice));
	}

	@Test
	void findByPriceLessThan() {
		double maxPrice = 10000;
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		when(productService.findByPriceLessThan(maxPrice)).thenReturn(productLists);
		assertEquals(productLists, productResource.findByPriceLessThan(maxPrice));
	}
}
