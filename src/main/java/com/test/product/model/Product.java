package com.test.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "PRODUCT_ID", nullable = false, length = 3)
	private Integer productId;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "PRODUCT_BRANCH")
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
