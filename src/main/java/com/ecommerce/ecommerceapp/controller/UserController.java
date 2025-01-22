package com.ecommerce.ecommerceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommerceapp.apimodel.ApiResponse;
import com.ecommerce.ecommerceapp.apimodel.UserRegistration;
import com.ecommerce.ecommerceapp.apimodel.UserResponse;
import com.ecommerce.ecommerceapp.apimodel.UserUpdateRequest;
import com.ecommerce.ecommerceapp.datamodel.LocalUser;
import com.ecommerce.ecommerceapp.exception.AlreadyExistsException;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/get/{user_id}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long user_id){
		try {
			LocalUser user = userService.getUserById(user_id);
			UserResponse desiredUser = UserResponse.fromEntityToDto(user);
			return ResponseEntity.ok(new ApiResponse(true, "User details retrieved successfully", desiredUser));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 
					"An unexpected error occurred. Please try again", ex.getMessage()));
		}
	}
	
	@PutMapping("/update/{user_id}")
	public ResponseEntity<ApiResponse> updateExistingUser(@RequestBody UserUpdateRequest request, @PathVariable Long user_id){
		try {
			LocalUser user = userService.updateRequestById(request, user_id);
			UserResponse updatedUser = UserResponse.fromEntityToDto(user);
			return ResponseEntity.ok(new ApiResponse(true, "User updated successfully", updatedUser));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 
					"An unexpected error occurred. Please try again"));
		}
	}
	
	@DeleteMapping("/delete/{user_id}")
	public ResponseEntity<ApiResponse>  deleteUserById(@PathVariable Long user_id) {
		try {
			userService.deleteUserById(user_id);
			return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 
					"An unexpected error occurred. Please try again"));
		}
	}
	
	

}
