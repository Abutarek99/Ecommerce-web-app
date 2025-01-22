package com.ecommerce.ecommerceapp.service;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerceapp.datamodel.Cart;
import com.ecommerce.ecommerceapp.datamodel.LocalUser;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.repository.CartRepository;
import com.ecommerce.ecommerceapp.repository.CartitemRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	
	//GET CART BY ID
	public Cart getCartById(Long id) {
		return cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The specified category was not found in the system"));
	}
	
	//CLEAR CART BY ID	
	@Transactional
	public void clearCart(Long id) {
		Cart cart = cartRepository.findById(id)
		         .orElseThrow(() -> new ResourceNotFoundException("The category you are trying to delete was not found"));
		
		cart.getCartItems().clear();
		cartRepository.deleteById(id);
	}
	
	//GET THE TOTAL AMOUNT OF A CART
	public BigDecimal getTotalAmount(Long id) {
		Cart cart = getCartById(id);
		return cart.getTotalAmount();
	}
	
	//GET THE CART ASSOCIATED WITH A USER
	public Cart getUserCart(Long userId) {
		 return cartRepository.findByUserId(userId);
	}
	
	public Cart initializeNewCart(LocalUser user) {
		return Optional.ofNullable( getUserCart(user.getId()))
		     .orElseGet(()->{
		    	 Cart cart = new Cart();
		    	 cart.setUser(user);
		    	return cartRepository.save(cart);
		});
	}

}
