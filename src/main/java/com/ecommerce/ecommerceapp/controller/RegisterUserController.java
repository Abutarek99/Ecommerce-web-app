package com.ecommerce.ecommerceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerceapp.apimodel.ApiResponse;
import com.ecommerce.ecommerceapp.apimodel.UserRegistration;
import com.ecommerce.ecommerceapp.exception.AlreadyExistsException;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.service.UserService;

@RestController
@RequestMapping("/registration")
public class RegisterUserController {
	@Autowired
	private UserService userService;
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistration userDetails){
		try {
			userService.Registration(userDetails);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "User registered successfully"));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(AlreadyExistsException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(false, ex.getMessage()));
		}
		
	}

}
