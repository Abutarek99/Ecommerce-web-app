package com.ecommerce.ecommerceapp.apimodel;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import com.ecommerce.ecommerceapp.datamodel.Cart;
import com.ecommerce.ecommerceapp.datamodel.CartItem;
import com.ecommerce.ecommerceapp.datamodel.LocalUser;;

public class CartDto {
	private Long id;
	private BigDecimal totalAmount;
	private UserResponseWithoutCartDetails userDetails;
	private Set<CartItemDto> items;
	
	public CartDto() {
		super();
	}

	public CartDto(Long id, BigDecimal totalAmount, UserResponseWithoutCartDetails userDetails, Set<CartItemDto> items) {
		super();
		this.id = id;
		this.totalAmount = totalAmount;
		this.items = items;
		this.userDetails = userDetails;
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
	

	
	public UserResponseWithoutCartDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserResponseWithoutCartDetails userDetails) {
		this.userDetails = userDetails;
	}

	//FROM ENTITY CART TO DTO CART WITH USER DETAILS
	public static CartDto fromEntityToDtoWithUserDetails(Cart cart) {
		Set<CartItemDto> items = cart.getCartItems()
		     .stream()
		     .map(CartItemDto:: fromEntityToDto)
		     .collect(Collectors.toSet());
		UserResponseWithoutCartDetails user = UserResponseWithoutCartDetails.fromEntityToDto(cart.getUser());
		return new CartDto(cart.getId(), cart.getTotalAmount(), user, items);
		     
	}


}
