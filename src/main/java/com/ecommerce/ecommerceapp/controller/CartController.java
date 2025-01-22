package com.ecommerce.ecommerceapp.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommerceapp.apimodel.ApiResponse;
import com.ecommerce.ecommerceapp.apimodel.CartDto;
import com.ecommerce.ecommerceapp.datamodel.Cart;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ApiResponse> getCartById(@PathVariable long id){
		try {
			Cart cart = cartService.getCartById(id);
			CartDto desiredCart = CartDto.fromEntityToDtoWithUserDetails(cart);
			return ResponseEntity.ok(new ApiResponse(true, "Cart found successfully", desiredCart));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 
					ex.getMessage()));
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteCartById(@PathVariable Long id){
		try {
			cartService.clearCart(id);
			return ResponseEntity.ok(new ApiResponse(true, "Cart deleted successfully"));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 
					ex.getMessage()));
		}
	}
	
	@GetMapping("/total-price/{id}")
	public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long id) {
		try {
			BigDecimal totalAmount = cartService.getTotalAmount(id);
			return ResponseEntity.ok(new ApiResponse(true, "The total amount of your cart has been successfully retrieved",
					"the total is : " + totalAmount));
		}
		catch(ResourceNotFoundException ex) {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 
					ex.getMessage()));
		}
	}
	

}
