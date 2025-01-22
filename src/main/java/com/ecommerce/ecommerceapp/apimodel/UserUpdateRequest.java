package com.ecommerce.ecommerceapp.apimodel;

public class UserUpdateRequest {
	private String firstName;
	private String lastName;
	
	public UserUpdateRequest() {
		super();
	}

	public UserUpdateRequest(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
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

	
	
}
