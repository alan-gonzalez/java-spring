package com.test.product.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class CustomErrorResponseDto implements Serializable {

	private static final long serialVersionUID = 7691999722619782553L;

	private HttpStatus httpStatus;

	private String errorMessage;

	public CustomErrorResponseDto(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.errorMessage = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
