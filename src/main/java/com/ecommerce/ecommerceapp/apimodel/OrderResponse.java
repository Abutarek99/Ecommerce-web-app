package com.ecommerce.ecommerceapp.apimodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.ecommerceapp.datamodel.WebOrder;
import com.ecommerce.ecommerceapp.enums.OrderStatus;

public class OrderResponse {
	private Long id;
	private LocalDate orderDate;
	private BigDecimal totalAmount;
	private Set<OrderitemDto> items;
	private OrderStatus orderStatus;
	private  UserResponseWithoutCartDetails userDetails;
	

	public OrderResponse(Long id, LocalDate orderDate, BigDecimal totalAmount, Set<OrderitemDto> items,
			OrderStatus orderStatus,  UserResponseWithoutCartDetails userDetails) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.items = items;
		this.orderStatus = orderStatus;
		this.userDetails = userDetails;
	}

	public  UserResponseWithoutCartDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails( UserResponseWithoutCartDetails userDetails) {
		this.userDetails = userDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Set<OrderitemDto> getItems() {
		return items;
	}

	public void setItems(Set<OrderitemDto> items) {
		this.items = items;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	//FROM ORDER ENTITY TO ORDER DTO
	public static OrderResponse fromEntityToDto(WebOrder order) {
		UserResponseWithoutCartDetails userDetails = UserResponseWithoutCartDetails.fromEntityToDto(order.getUser());
		Set<OrderitemDto> items = order.getOrderItems().stream()
		      .map(OrderitemDto::fromEntityToDto)
		      .collect(Collectors.toSet());
		return new OrderResponse(order.getId(), order.getOrderDate(), order.getTotlaAmount(), items, order.getOrderStatus(),
				userDetails);	      
	}
	
	

}
