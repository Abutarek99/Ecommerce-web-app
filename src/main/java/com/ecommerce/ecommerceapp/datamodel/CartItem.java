package com.ecommerce.ecommerceapp.datamodel;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	@Column(nullable = false)
	private Integer quantity;
	@Column(name = "unit_price", nullable = false)
	private BigDecimal unitPrice;
	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	
	public CartItem() {
		super();
	}

	public CartItem(Long id, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice, Cart cart, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.cart = cart;
		this.product = product;
	}

	public void setTotalPrice() {
		this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity) );
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}


	public Cart getCart() {
		return cart;
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	
}
