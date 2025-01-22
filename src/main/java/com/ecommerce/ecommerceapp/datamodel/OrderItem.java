package com.ecommerce.ecommerceapp.datamodel;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int quantity;
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private WebOrder order;
	@ManyToOne()
	@JoinColumn(name = "product_id")
	private Product product;
	
	public OrderItem() {
		super();
	}

	public OrderItem(Product product, int quantity, BigDecimal price, WebOrder order) {
		super();
		this.price = price;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public WebOrder getOrder() {
		return order;
	}

	public void setOrder(WebOrder order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	

}
