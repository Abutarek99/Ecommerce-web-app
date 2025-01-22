package com.ecommerce.ecommerceapp.security.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerceapp.datamodel.LocalUser;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.repository.UserRepository;

@Component
public class LocalUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		LocalUser user = Optional.ofNullable(userRepository.findByemail(userName))
		                     .orElseThrow(()-> new ResourceNotFoundException("User not found"));
		return LocalUserDetails.buildUserDetails(user);
	}
	

}
