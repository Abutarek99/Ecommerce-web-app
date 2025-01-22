package com.ecommerce.ecommerceapp.apimodel;

public class ApiResponse {
	private boolean success;
	private String message;
	private Object object;
	
	public ApiResponse(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public ApiResponse(boolean success, String message, Object object) {
		super();
		this.success = success;
		this.message = message;
		this.object = object;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	


}
