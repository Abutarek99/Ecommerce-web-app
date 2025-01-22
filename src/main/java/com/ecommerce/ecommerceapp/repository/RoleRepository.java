package com.ecommerce.ecommerceapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerceapp.datamodel.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	public Optional<Role> findByname(String name);

}
