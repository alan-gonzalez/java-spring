package com.test.product.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.test.product.dto.CustomErrorResponseDto;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(ResourceException.class)
	public ResponseEntity<CustomErrorResponseDto> handleException(ResourceException e) {

		CustomErrorResponseDto errorResponse = new CustomErrorResponseDto(e.getHttpStatus(), e.getMessage());

		return new ResponseEntity<>(errorResponse, e.getHttpStatus());
	}
}
