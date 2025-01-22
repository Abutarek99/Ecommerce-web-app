package com.ecommerce.ecommerceapp.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommerceapp.apimodel.ApiResponse;
import com.ecommerce.ecommerceapp.apimodel.CartItemDto;
import com.ecommerce.ecommerceapp.datamodel.Cart;
import com.ecommerce.ecommerceapp.datamodel.CartItem;
import com.ecommerce.ecommerceapp.datamodel.LocalUser;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.service.CartService;
import com.ecommerce.ecommerceapp.service.CartitemService;
import com.ecommerce.ecommerceapp.service.UserService;

import io.jsonwebtoken.JwtException;

@RestController
@RequestMapping("/cart_items")
public class CartitemController {
	@Autowired
	private CartitemService cartitemService;
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam long product_id, @RequestParam int quantity){
		try {
			LocalUser user = userService.getAuthenticatedUser();
			Cart cart = cartService.initializeNewCart(user);
			cartitemService.addItemToCart(cart.getId(), product_id, quantity);
			return ResponseEntity.ok(new ApiResponse(true, "Item added successfully"));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(JwtException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, ex.getMessage()));
		}
	}
	
	@DeleteMapping("remove/cart/{cart_id}/item/{product_id}")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cart_id, @PathVariable Long product_id){
		try {
			cartitemService.removeItemFromCart(cart_id, product_id);
			return ResponseEntity.ok(new ApiResponse(true, "Item deleted successfully"));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
		}
	}
	
	@PutMapping("update/cart/{cart_id}/item/{product_id}")
	public  ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cart_id, @PathVariable Long product_id, 
			@RequestParam Integer quantity){
		try {
			cartitemService.updateQuantityItem(cart_id, product_id, quantity);
			return ResponseEntity.ok(new ApiResponse(true, "Quantity updated successfully"));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage()));
		}
	}
	
	@GetMapping("get/cart/{cart_id}/item/{product_id}")
	public ResponseEntity<ApiResponse> getCartItemByCartid(@PathVariable Long cart_id, @PathVariable Long product_id ){
		try {
			CartItem cartItem = cartitemService.getCartItem(cart_id, product_id);
			CartItemDto desiredCartitem = CartItemDto.fromEntityToDto(cartItem);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,
					"The item has been successfully found", desiredCartitem));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
	}
	
	@GetMapping("/get_all/{cart_id}")
	public ResponseEntity<ApiResponse>getItemsByCartid(@PathVariable Long cart_id){
		try {
			Set<CartItem> desiredItems = cartitemService.getItemsByCartid(cart_id);
			Set<CartItemDto> desiredItemsDto = CartItemDto.fromEntityListToDtoList(desiredItems);
			return ResponseEntity.ok(new ApiResponse(true, "Successfully retrieved all items related to the specified cart ID", desiredItemsDto));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
	}
		
	
}
