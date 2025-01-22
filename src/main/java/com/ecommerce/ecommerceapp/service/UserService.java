package com.ecommerce.ecommerceapp.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerceapp.apimodel.UserRegistration;
import com.ecommerce.ecommerceapp.apimodel.UserUpdateRequest;
import com.ecommerce.ecommerceapp.datamodel.LocalUser;
import com.ecommerce.ecommerceapp.datamodel.Role;
import com.ecommerce.ecommerceapp.exception.AlreadyExistsException;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.repository.RoleRepository;
import com.ecommerce.ecommerceapp.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	
	public void Registration(UserRegistration userDetails) {
		if(userRepository.existsByemail(userDetails.getEmail()))
			throw new AlreadyExistsException("This user already exists");
		LocalUser newUser = new LocalUser(userDetails.getFirstName(), userDetails.getLastName(),
				userDetails.getEmail(), passwordEncoder.encode(userDetails.getPassword()));
		String roleName = userDetails.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
		Role role = roleRepository.findByname(roleName)
		                .orElseThrow(()-> new ResourceNotFoundException("Role" + roleName + "not found"));
		newUser.setRoles(Set.of(role));
		userRepository.save(newUser);
	}

	//GET USER BY ID
	public LocalUser getUserById(Long id) {
		return userRepository.findById(id)
		           .orElseThrow(()-> new ResourceNotFoundException("No user found with the provided ID"));
	}
	
	
    //UPDATE USER BY ID
	public LocalUser updateRequestById(UserUpdateRequest request, Long userId) {
		return userRepository.findById(userId).map(existingUser-> {
			existingUser.setFirstName(request.getFirstName());
			existingUser.setLastName(request.getLastName());
			return userRepository.save(existingUser);
		}).orElseThrow(()-> new ResourceNotFoundException("No user found with the provided ID"));
	}
	
	//DELETE USER BY ID
	public void deleteUserById(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository :: delete,
				()->{ throw new ResourceNotFoundException("User not found. Please verify the user ID and try again.");});
	}


	public LocalUser getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepository.findByemail(email);
	}
}
