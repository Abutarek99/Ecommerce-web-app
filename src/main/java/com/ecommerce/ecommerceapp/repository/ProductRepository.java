package com.ecommerce.ecommerceapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceapp.datamodel.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public Optional<Product> findByname(String name);
	public List<Product>findByCategoryName(String categoryName);
	public List<Product> findBybrand(String brand);
	public List<Product> findByCategoryNameAndBrand(String category, String brand);
    public Product findByNameAndBrand(String name, String brand);
    public long countByBrandAndName(String brand, String name);
    public boolean existsByNameAndBrand(String name, String brand);
}
