package com.test.product.service;

import java.util.List;

import com.test.product.dto.ProductDto;
import com.test.product.exception.ResourceException;

public interface ProductService {

	ProductDto saveProduct(ProductDto productDto);
	
	ProductDto updateProduct(Integer productId, ProductDto productDto) throws ResourceException;
	
	List<ProductDto> productByBranch(String branch) throws ResourceException;
}
