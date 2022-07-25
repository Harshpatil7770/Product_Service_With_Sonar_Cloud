package com.xoriant.delivery.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xoriant.delivery.dto.ProductDTO;
import com.xoriant.delivery.model.Product;
import com.xoriant.delivery.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@PostMapping("/save/{brandId}/{categoryId}")
	public ResponseEntity<String> addNewProduct(@RequestBody ProductDTO productDTO, @PathVariable int brandId,
			@PathVariable int categoryId) {
		String result = productService.addNewProduct(productDTO, brandId, categoryId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/saveAll/{brandId}/{categoryId}")
	public ResponseEntity<String> addNewListsProducts(@RequestBody List<ProductDTO> productDTO,
			@PathVariable int brandId, @PathVariable int categoryId) {
		String result = productService.addNewListsProducts(productDTO, brandId, categoryId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/update/{brandId}/{categoryId}")
	public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable int brandId,
			@PathVariable int categoryId) {
		String result = productService.updateProduct(productDTO, brandId, categoryId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/updateAll/{brandId}/{categoryId}")
	public ResponseEntity<String> updateListsProduct(@RequestBody List<ProductDTO> productDTO,
			@PathVariable int brandId, @PathVariable int categoryId) {
		String result = productService.updateListsProduct(productDTO, brandId, categoryId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{productId}")
	public void deleteProduct(@PathVariable int productId) {
		productService.deleteProduct(productId);
	}

	@GetMapping("/find/{productName}")
	public Optional<Product> findByProductName(@PathVariable String productName) {
		return productService.findByProductName(productName);
	}

	@GetMapping("/find-product/{productId}")
	public Optional<Product> findByProductId(@PathVariable int productId) {
		return productService.findByProductId(productId);
	}

	@GetMapping("/findAll")
	public List<Product> findAllProducts() {
		return productService.findAllProducts();
	}

	@GetMapping("/findAll/{brandName}")
	public List<Product> findByBrandName(@PathVariable String brandName) {
		return productService.findByBrandName(brandName);
	}

	@GetMapping("/findAll/products/{categoryName}")
	public List<Product> findByCategoryName(@PathVariable String categoryName) {
		return productService.findByCategoryName(categoryName);
	}

	@GetMapping("/findAll/{minPrice}/{maxPrice}")
	public List<Product> findByPriceInBetween(@PathVariable double minPrice, @PathVariable double maxPrice) {
		return productService.findByPriceInBetween(minPrice, maxPrice);
	}

	@GetMapping("/find/products/{minPrice}")
	public List<Product> findByPriceGreaterThan(@PathVariable double minPrice) {
		return productService.findByPriceGreaterThan(minPrice);
	}

	@GetMapping("/find/products/maxPrice/{minPrice}")
	public List<Product> findByPriceLessThan(@PathVariable double minPrice) {
		return productService.findByPriceLessThan(minPrice);
	}
}
