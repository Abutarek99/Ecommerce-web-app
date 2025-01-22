package com.ecommerce.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommerceapp.datamodel.OrderItem;

@Repository
public interface OrderitemRepository extends JpaRepository<OrderItem, Long>{

}
