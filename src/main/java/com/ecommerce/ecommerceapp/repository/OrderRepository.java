package com.ecommerce.ecommerceapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceapp.datamodel.WebOrder;

import jakarta.persistence.criteria.Order;

@Repository
public interface OrderRepository  extends JpaRepository<WebOrder, Long> {
	public Optional<List<WebOrder>> findByUserId(Long user_id);

}
