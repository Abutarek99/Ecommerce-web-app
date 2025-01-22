package com.ecommerce.ecommerceapp.apimodel;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	
	public LoginRequest() {
		super();
	}

	public LoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
