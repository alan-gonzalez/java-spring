package com.test.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.product.dto.ProductDto;
import com.test.product.exception.ResourceException;
import com.test.product.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("save")
	public ProductDto saveProduct(@RequestBody ProductDto productDto) {
		return productService.saveProduct(productDto);
	}

	@PutMapping("/{productId}/update")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable Integer productId, @RequestBody ProductDto productDto)
			throws ResourceException {
		return ResponseEntity.ok().body(productService.updateProduct(productId, productDto));
	}

	@GetMapping
	public ResponseEntity<List<ProductDto>> getByBranch(
			@RequestHeader(value = "ID_CLIENT_SESSION") Long clientSession, 
			@RequestParam String branch) throws ResourceException {
		
		if (clientSession != 78965088) {
			throw new ResourceException(HttpStatus.BAD_REQUEST, "Sesión no valida");
		}
		return ResponseEntity.ok().body(productService.productByBranch(branch));
	}

}
