package com.ecommerce.ecommerceapp.datamodel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.ecommerce.ecommerceapp.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class WebOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal totalAmount;
	private LocalDate orderDate;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItem> orderItems = new HashSet<>();
	@ManyToOne
	@JoinColumn(name = "user_id")
	private LocalUser user;
	
	public WebOrder() {
		super();
	}

	public WebOrder(Long id, LocalUser user, BigDecimal totalAmount, LocalDate orderDate, OrderStatus orderStatus) {
		super();
		this.id = id;
		this.totalAmount = totalAmount;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotlaAmount() {
		return totalAmount;
	}

	public void setTotlaAmount(BigDecimal totlaAmount) {
		this.totalAmount = totlaAmount;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public LocalUser getUser() {
		return user;
	}

	public void setUser(LocalUser user) {
		this.user = user;
	}
	
	
	
	

}
