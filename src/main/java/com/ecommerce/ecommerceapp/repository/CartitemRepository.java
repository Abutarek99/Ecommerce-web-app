package com.ecommerce.ecommerceapp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceapp.datamodel.CartItem;

@Repository
public interface CartitemRepository extends JpaRepository<CartItem, Long>{
	public Set<CartItem> findByCartId(Long cart_id);
	public void deleteAllByCartId(Long cart_id);
	
}
