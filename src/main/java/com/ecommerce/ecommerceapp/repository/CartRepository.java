package com.ecommerce.ecommerceapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerceapp.datamodel.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	public Cart findByUserId(Long user_id);

}
