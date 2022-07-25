package com.xoriant.delivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
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

import com.xoriant.delivery.constant.Constant;
import com.xoriant.delivery.dao.ProductRepo;
import com.xoriant.delivery.dto.Brand;
import com.xoriant.delivery.dto.Category;
import com.xoriant.delivery.dto.ProductDTO;
import com.xoriant.delivery.exception.ElementNotFound;
import com.xoriant.delivery.exception.InputUserException;
import com.xoriant.delivery.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	@Mock
	private ProductRepo productRepo;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Mock
	private BrandCategoryServiceProxy brandCategoryServiceProxy;

	private ProductDTO productDTO;
	private ProductDTO productDTO1;

	private Product product;
	private Product product1;

	private Brand brand;

	private Category category;

	static final int BRAND_ID = 501;
	static final int CATEGORY_ID = 601;

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
		when(productRepo.save(product)).thenReturn(product);
		findByBrandId();
		findByCategoryId();
		assertEquals(Constant.NEW_PRODUCT_ADDED, productServiceImpl.addNewProduct(productDTO, BRAND_ID, CATEGORY_ID));

	}

	@Test
	void findByBrandId() {
		Optional<Brand> existingBrand = Optional.of(brand);
		when(brandCategoryServiceProxy.findByBrandId(BRAND_ID)).thenReturn(existingBrand);
		Optional<Brand> result = brandCategoryServiceProxy.findByBrandId(BRAND_ID);
		assertNotNull(result);
		assertEquals(result, productServiceImpl.findByBrandId(BRAND_ID));
	}

	@Test
	void findByCategoryId() {
		Optional<Category> existingCategory = Optional.of(category);
		when(brandCategoryServiceProxy.findByCategoryId(CATEGORY_ID)).thenReturn(existingCategory);
		Optional<Category> result = brandCategoryServiceProxy.findByCategoryId(CATEGORY_ID);
		assertEquals(result, productServiceImpl.findByCategoryId(CATEGORY_ID));
	}

	@Test
	void addNewProduct_throwException_if_ProductNameIsEmpty() {

		product.setProductName(null);
		doThrow(InputUserException.class).when(productRepo).save(product);
		assertThrows(InputUserException.class, () -> {
			productRepo.save(product);
		});
	}

	@Test
	void addNewProduct_throwException_if_ProductNameIsBlank() {

		product.setProductName("  ");
		doThrow(InputUserException.class).when(productRepo).save(product);
		assertThrows(InputUserException.class, () -> {
			productRepo.save(product);
		});
	}

	@Test
	void findByCategoryId_throwExcpetion_if_categoryNotFoundInDatabase() {
		int categoryId = 1;
		doThrow(ElementNotFound.class).when(brandCategoryServiceProxy).findByCategoryId(categoryId);
		assertThrows(ElementNotFound.class, () -> {
			brandCategoryServiceProxy.findByCategoryId(categoryId);
		});
	}

	@Test
	void addNewListsProducts() {
		List<ProductDTO> productLists = new ArrayList<>();
		productLists.add(productDTO);
		productLists.add(productDTO1);

		for (ProductDTO newProduct : productLists) {
			product = new Product(newProduct.getProductId(), newProduct.getProductName(), newProduct.getPrice(),
					newProduct.getQuantity(), newProduct.getKeywords(), newProduct.getBrandName(),
					newProduct.getCategoryName());
			findByBrandId();
			findByCategoryId();
			when(productRepo.save(product)).thenReturn(product);
		}

		assertEquals(Constant.NEW_LISTS_OF_PRODUCT_ADDED,
				productServiceImpl.addNewListsProducts(productLists, BRAND_ID, CATEGORY_ID));
	}

	@Test
	void updateProduct() {
		int productId = 101;
		Optional<Product> existingProduct = Optional.of(product);
		when(productRepo.findById(productId)).thenReturn(existingProduct);
		Optional<Product> result = productRepo.findById(productId);
		assertNotNull(result);
		product.setProductName("Oppo Reno");
		when(productRepo.save(product)).thenReturn(product);
		findByBrandId();
		findByCategoryId();
		assertEquals(Constant.UPDATE_EXISTING_PRODUCT,
				productServiceImpl.updateProduct(productDTO, BRAND_ID, CATEGORY_ID));
	}

	@Test
	void updateListsProduct() {
		List<ProductDTO> productLists = new ArrayList<>();
		int productId = 101;
		int productId1 = 102;
		Optional<Product> existingProduct = Optional.of(product);
		when(productRepo.findById(productId)).thenReturn(existingProduct);
		Optional<Product> result = productRepo.findById(productId);
		assertNotNull(result);
		productDTO.setProductName("Oppo Reno");
		productLists.add(productDTO);

		Optional<Product> existingProduct1 = Optional.of(product1);
		when(productRepo.findById(productId1)).thenReturn(existingProduct);
		Optional<Product> result1 = productRepo.findById(productId1);
		assertNotNull(result1);
		productDTO1.setProductName("Oppo Reno7");
		productLists.add(productDTO1);

		for (ProductDTO updateProduct : productLists) {

			product = new Product(updateProduct.getProductId(), updateProduct.getProductName(),
					updateProduct.getPrice(), updateProduct.getQuantity(), updateProduct.getKeywords(),
					updateProduct.getBrandName(), updateProduct.getCategoryName());
			findByBrandId();
			findByCategoryId();
			when(productRepo.save(product)).thenReturn(product);
		}

		assertEquals(Constant.UPDATE_LISTS_OF_PRODUCTS,
				productServiceImpl.updateListsProduct(productLists, BRAND_ID, CATEGORY_ID));

	}

	@Test
	void deleteProduct() {
		int productId = 101;
		Optional<Product> existingProduct = Optional.of(product);
		when(productRepo.findById(productId)).thenReturn(existingProduct);
		productServiceImpl.deleteProduct(productId);
		verify(productRepo, times(1)).deleteById(productId);
	}

	@Test
	void deleteProduct_throwsException_if_productIdNotFoundInDatabase() {

		int productId = 1001;
		doThrow(ElementNotFound.class).when(productRepo).findById(productId);
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findById(productId);
		});
	}

	@Test
	void findByProductName() {
		String productName = "Oppo F1f";
		Optional<Product> existingProduct = Optional.of(product);
		when(productRepo.findByProductName(productName)).thenReturn(existingProduct);
		Optional<Product> result = productRepo.findByProductName(productName);
		assertNotNull(result);
		assertEquals(result, productServiceImpl.findByProductName(productName));
	}

	@Test
	void findByProductName_throwsException_if_ProductNameNotPresentInDatabase() {
		String productName = "One Plus";
		doThrow(ElementNotFound.class).when(productRepo).findByProductName(productName);
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findByProductName(productName);
		});
	}

	@Test
	void findByProductId() {
		int productId = 101;
		Optional<Product> existingProduct = Optional.of(product);
		when(productRepo.findById(productId)).thenReturn(existingProduct);
		Optional<Product> result = productRepo.findById(productId);
		assertNotNull(result);
		assertEquals(result, productServiceImpl.findByProductId(productId));
	}

	@Test
	void findByProductId_throwsException_if_productIdNotFoundInDatabase() {

		int productId = 1001;
		doThrow(ElementNotFound.class).when(productRepo).findById(productId);
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findById(productId);
		});
	}

	@Test
	void findAllProducts() {
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		when(productRepo.findAll()).thenReturn(productLists);
		assertEquals(2, productRepo.findAll().size());
	}

	@Test
	void findAllProducts_throwException_if_ProductListsNotFoundInDatabase() {
		doThrow(ElementNotFound.class).when(productRepo).findAll();
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findAll();
		});
	}

	@Test
	void findByBrandName() {
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		String brandName = "Oppo";
		when(productRepo.findByBrandName(brandName)).thenReturn(productLists);
		assertEquals(productLists, productServiceImpl.findByBrandName(brandName));
	}

	@Test
	void findByBrandName_throwsException_if_ProductNameNotPresentInDatabase() {
		String brandName = "Samsung";
		doThrow(ElementNotFound.class).when(productRepo).findByProductName(brandName);
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findByProductName(brandName);
		});
	}

	@Test
	void findByCategoryName() {
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		String categoryName = "Mobile";
		when(productRepo.findByCategoryName(categoryName)).thenReturn(productLists);
		assertEquals(productLists, productServiceImpl.findByCategoryName(categoryName));
	}

	@Test
	void findByCategoryName_throwsException_if_ProductNameNotPresentInDatabase() {
		String categoryName = "Mobile";
		doThrow(ElementNotFound.class).when(productRepo).findByProductName(categoryName);
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findByProductName(categoryName);
		});
	}

	@Test
	void findByPriceInBetween() {
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		double minPrice = 10000;
		double maxPrice = 20000;

		when(productRepo.findByPriceIsBetween(minPrice, maxPrice)).thenReturn(productLists);
		assertEquals(productLists, productServiceImpl.findByPriceInBetween(minPrice, maxPrice));
	}

	@Test
	void findByPriceInBetween_throwsException_if_ProductNotPresentInDatabase() {
		double minPrice = 2000;
		double maxPrice = 10000;
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);

		doThrow(ElementNotFound.class).when(productRepo).findByPriceIsBetween(minPrice, maxPrice);
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findByPriceIsBetween(minPrice, maxPrice);
		});
	}

	@Test
	void findByPriceGreaterThan() {
		double minPrice = 10000;
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		when(productRepo.findByPriceGreaterThan(minPrice)).thenReturn(productLists);
		assertEquals(productLists, productServiceImpl.findByPriceGreaterThan(minPrice));
	}

	@Test
	void findByPriceGreaterThan_throwsException_if_ProductPriceNotPresentInDatabase() {
		double minPrice = 2000;
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);

		doThrow(ElementNotFound.class).when(productRepo).findByPriceGreaterThan(minPrice);
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findByPriceGreaterThan(minPrice);
		});
	}

	@Test
	void findByPriceLessThan() {
		double maxPrice = 10000;
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);
		when(productRepo.findByPriceGreaterThan(maxPrice)).thenReturn(productLists);
		assertEquals(productLists, productServiceImpl.findByPriceGreaterThan(maxPrice));
	}

	@Test
	void findByPriceLessThan_throwsException_if_ProductPriceNotMatchesInDatabase() {
		double maxPrice = 10000;
		List<Product> productLists = new ArrayList<>();
		productLists.add(product);
		productLists.add(product1);

		doThrow(ElementNotFound.class).when(productRepo).findByPriceLessThan(maxPrice);
		assertThrows(ElementNotFound.class, () -> {
			productRepo.findByPriceLessThan(maxPrice);
		});
	}

}
