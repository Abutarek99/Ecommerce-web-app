package com.ecommerce.ecommerceapp.apimodel;

import com.ecommerce.ecommerceapp.datamodel.LocalUser;

public class UserResponseWithoutCartDetails {
	private long id;
	private String firstName;
	private String lastName;
	
	public UserResponseWithoutCartDetails() {
		super();
	}

	public UserResponseWithoutCartDetails(long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	//FROM ENTITY TO DTO
	public static UserResponseWithoutCartDetails fromEntityToDto(LocalUser user) {
		return new UserResponseWithoutCartDetails(user.getId(), user.getFirstName(), user.getLastName());
	}
	
}
