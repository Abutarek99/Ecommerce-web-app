package com.ecommerce.ecommerceapp.apimodel;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.ecommerceapp.datamodel.Cart;

public class CartDtoWithoutUserDetails {
	private Long id;
	private BigDecimal totalAmount;
	private Set<CartItemDto> items;


	public CartDtoWithoutUserDetails() {
		super();
	}
	
	public CartDtoWithoutUserDetails(Long id, BigDecimal totalAmount, Set<CartItemDto> items) {
		super();
		this.id = id;
		this.totalAmount = totalAmount;
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Set<CartItemDto> getItems() {
		return items;
	}

	public void setItems(Set<CartItemDto> items) {
		this.items = items;
	}

	public static CartDtoWithoutUserDetails fromEntityToDtoWithoutUserDetails(Cart cart) {
		Set<CartItemDto> items = cart.getCartItems()
		     .stream()
		     .map(CartItemDto:: fromEntityToDto)
		     .collect(Collectors.toSet());
		return new CartDtoWithoutUserDetails(cart.getId(), cart.getTotalAmount(), items);
		     
	}
	

}
