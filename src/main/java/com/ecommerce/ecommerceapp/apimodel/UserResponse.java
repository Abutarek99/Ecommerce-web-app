package com.ecommerce.ecommerceapp.apimodel;

import com.ecommerce.ecommerceapp.datamodel.LocalUser;

public class UserResponse {
	private Long id;
	private String firstName;
	private String lastName;
	private CartDtoWithoutUserDetails cartDetails;
	
	public UserResponse() {
		super();
	}

	public UserResponse(Long id, String firstName, String lastName, CartDtoWithoutUserDetails cartDetails) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cartDetails = cartDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

	
	public CartDtoWithoutUserDetails getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(CartDtoWithoutUserDetails cartDetails) {
		this.cartDetails = cartDetails;
	}

	//FROM USER ENTITY TO USER DTO
	public static UserResponse fromEntityToDto(LocalUser user) {
		return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), 
				CartDtoWithoutUserDetails.fromEntityToDtoWithoutUserDetails(user.getCart()));
	}

}
