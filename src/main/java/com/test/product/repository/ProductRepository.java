package com.test.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Optional<List<Product>> findByProductBranch(String productBranch);
	
}
