package com.ecommerce.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerceapp.datamodel.LocalUser;

public interface UserRepository extends JpaRepository<LocalUser, Long>{
	boolean existsByemail(String userEmail);
	LocalUser findByemail(String email);
}
