package com.ecommerce.ecommerceapp.apimodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.ecommerce.ecommerceapp.datamodel.OrderItem;
import com.ecommerce.ecommerceapp.datamodel.WebOrder;
import com.ecommerce.ecommerceapp.enums.OrderStatus;

public class OrderResponseWithoutUserDetails {
	private Long id;
	private LocalDate orderDate;
	private BigDecimal totalAmount;
	private Set<OrderitemDto> items;
	private OrderStatus orderStatus;
	
	public OrderResponseWithoutUserDetails() {
		super();
	}

	public OrderResponseWithoutUserDetails(Long id, LocalDate orderDate, BigDecimal totalAmount,
		Set<OrderitemDto> items, OrderStatus orderStatus) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.items = items;
		this.orderStatus = orderStatus;
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
	
	//FROM ORDER LIST ENTITY TO ORDER LIST DTO
	public static List<OrderResponseWithoutUserDetails> fromListEntityToDtoEntity(List<WebOrder> orders){
		Set<OrderItem> orderItems = orders.stream()
		        .flatMap(order -> order.getOrderItems().stream()) 
		        .collect(Collectors.toSet());
		
		Set<OrderitemDto> desiredOrderItems = OrderitemDto.fromEntitySetToDtoSet(orderItems);
		
		return orders.stream()
				     .map(order-> new OrderResponseWithoutUserDetails(order.getId(), order.getOrderDate(), 
				    			 order.getTotlaAmount(), desiredOrderItems, order.getOrderStatus()))
				     .toList();
	}

}
