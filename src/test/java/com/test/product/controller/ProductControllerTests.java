package com.test.product.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.test.product.dto.CustomErrorResponseDto;
import com.test.product.dto.ProductDto;
import com.test.product.exception.ResourceException;
import com.test.product.service.ProductService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTests {

	@MockBean
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@LocalServerPort
	private int port;
	
	private StringBuilder BASE_URL = new StringBuilder();
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@BeforeEach
	public void initData() {
		BASE_URL.append("http://localhost:").append(port).append("/api/v1/product");
	}
	
	@Test
	public void verifyCode200GetByBranch() throws ResourceException {
		
		ProductDto productDto =  new ProductDto();
		productDto.setId(1);
		productDto.setProductName("Test");
		productDto.setProductBranch("Test");
		
		when(productService.productByBranch("Test")).thenReturn(Arrays.asList(productDto));
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("ID_CLIENT_SESSION", "78965088");

	    HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
	    
	    ResponseEntity<List<ProductDto>> response =
	            restTemplate.exchange(BASE_URL + "?branch=AB", HttpMethod.GET, requestEntity,
	                new ParameterizedTypeReference<List<ProductDto>>() {});
	    
	    assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void verifyCode400NoValidSession() throws ResourceException {
		
		ProductDto productDto =  new ProductDto();
		productDto.setId(1);
		productDto.setProductName("Test");
		productDto.setProductBranch("Test");
		
		when(productService.productByBranch("Test")).thenThrow(new ResourceException(HttpStatus.BAD_REQUEST, "Sesión no valida"));
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("ID_CLIENT_SESSION", "78965089");

	    HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
	    
	    ResponseEntity<CustomErrorResponseDto> response =
	            restTemplate.exchange(BASE_URL + "?branch=AB", HttpMethod.GET, requestEntity,
	            		CustomErrorResponseDto.class);
	    
	    assertEquals(400, response.getStatusCodeValue());
	    assertEquals("Sesión no valida", response.getBody().getErrorMessage());
	}
}
