package com.ecommerce.ecommerceapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommerceapp.apimodel.ApiResponse;
import com.ecommerce.ecommerceapp.apimodel.OrderResponse;
import com.ecommerce.ecommerceapp.apimodel.OrderResponseWithoutUserDetails;
import com.ecommerce.ecommerceapp.datamodel.WebOrder;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/place")
	public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long user_id){
		try {
			OrderResponse orderMade = orderService.placeOrder(user_id);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Order placed successfully", orderMade), HttpStatus.CREATED);
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,
					"An unexpected error occurred. Please try again. ", ex.getMessage()));
		}
	}
	
	@GetMapping("/get/{order_id}")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long order_id) {
		try {
			OrderResponse desiredOrder = orderService.getOrderById(order_id);
			return ResponseEntity.ok().body(new ApiResponse(true, "Order retrieved successfully", desiredOrder));
		}
		catch(ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, 
					"An unexpected error occurred. Please try again"));
		}
	}
	
	@GetMapping("/get/user/{user_id}")
	public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long user_id){
		try {
			List<OrderResponseWithoutUserDetails> desiredOrders = orderService.getUserOrders(user_id);
			return ResponseEntity.ok(new ApiResponse(true, "Orders successfully retrieved for the specified user", desiredOrders));
		}
		catch(ResourceNotFoundException ex){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,  
					ex.getMessage()));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false
					, "An unexpected error occurred while retrieving orders", ex.getMessage()));
		}
		
	}

}
