package com.ecommerce.ecommerceapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.ecommerce.ecommerceapp.apimodel.JwtResponse;
import com.ecommerce.ecommerceapp.apimodel.LoginRequest;
import com.ecommerce.ecommerceapp.security.jwt.JwtUtils;
import com.ecommerce.ecommerceapp.security.user.LocalUserDetails;

import jakarta.validation.Valid;

@Service
public class AuthenticationService {
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtUtils jwtUtils;
	
	public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException{
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
				loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String userJwtToken = jwtUtils.generateTokenForUser(auth);
		LocalUserDetails userDetails = (LocalUserDetails)auth.getPrincipal();
		return new JwtResponse(userDetails.getId(), userJwtToken);
	}

}
