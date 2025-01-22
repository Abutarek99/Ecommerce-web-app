package com.ecommerce.ecommerceapp.exception;

public class ProductAlreadyExistsException extends RuntimeException {
	public ProductAlreadyExistsException(String message) {
		super(message);
	}

}
