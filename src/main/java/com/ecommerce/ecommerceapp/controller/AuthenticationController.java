package com.ecommerce.ecommerceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceapp.apimodel.ApiResponse;
import com.ecommerce.ecommerceapp.apimodel.JwtResponse;
import com.ecommerce.ecommerceapp.apimodel.LoginRequest;
import com.ecommerce.ecommerceapp.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationService authService;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest){
		try {
			JwtResponse jwtResponse = authService.login(loginRequest);
			return ResponseEntity.ok(new ApiResponse(true, "Login successful", jwtResponse ));
		}
		catch(AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 
					ex.getMessage()));
		}
	}

}
