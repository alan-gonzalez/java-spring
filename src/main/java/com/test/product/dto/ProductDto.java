package com.test.product.dto;

import java.io.Serializable;

public class ProductDto implements Serializable {

	private static final long serialVersionUID = 7691999722619782553L;

	private Integer productId;

	private String productName;

	private String productBranch;

	public Integer getId() {
		return productId;
	}

	public void setId(Integer id) {
		this.productId = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBranch() {
		return productBranch;
	}

	public void setProductBranch(String productBranch) {
		this.productBranch = productBranch;
	}

}
