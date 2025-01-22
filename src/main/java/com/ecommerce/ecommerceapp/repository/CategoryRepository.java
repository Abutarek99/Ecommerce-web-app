package com.ecommerce.ecommerceapp.repository;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceapp.datamodel.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	public Category findByname(String categoryName);
	public boolean existsByname(String categoryName);
}
