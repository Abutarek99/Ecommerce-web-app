package com.ecommerce.ecommerceapp.data;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerceapp.datamodel.LocalUser;
import com.ecommerce.ecommerceapp.datamodel.Role;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.repository.RoleRepository;
import com.ecommerce.ecommerceapp.repository.UserRepository;

@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		createDefaultRoleIfNotExists(Set.of("ROLE_ADMIN", "ROLE_USER"));
	}
	
	
	public void createDefaultRoleIfNotExists(Set<String> roles) {
		roles.stream()
		      .filter(role-> roleRepository.findByname(role).isEmpty())
		      .map(roleName ->
		              {
		                 Role newRole = new Role(roleName);
		                return newRole;
		               })
		      .forEach(roleRepository::save);  
	}
	

}
