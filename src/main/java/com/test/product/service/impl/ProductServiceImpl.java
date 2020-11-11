package com.test.product.service.impl;

import java.util.Arrays;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.test.product.dto.ProductDto;
import com.test.product.exception.ResourceException;
import com.test.product.model.Product;
import com.test.product.repository.ProductRepository;
import com.test.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDto saveProduct(ProductDto productDto) {

		Product product = modelMapper.map(productDto, Product.class);

		return modelMapper.map(productRepository.save(product), ProductDto.class);

	}

	@Override
	public ProductDto updateProduct(Integer productId, ProductDto productDto) throws ResourceException {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceException(HttpStatus.NOT_FOUND, "Product not foud"));

		product.setProductBranch(productDto.getProductBranch());
		product.setProductName(productDto.getProductName());

		return modelMapper.map(productRepository.save(product), ProductDto.class);
	}

	@Override
	public List<ProductDto> productByBranch(String branch) throws ResourceException {
		return Arrays.asList(modelMapper.map(productRepository.findByProductBranch(branch)
				.orElseThrow(() -> new ResourceException(HttpStatus.NOT_FOUND, "Product not foud")), ProductDto[].class));
	}

}
