package com.ecommerce.ecommerceapp.datamodel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	@Column(name = "total_amount")
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private LocalUser user;
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval  = true)
	private Set<CartItem> cartItems = new HashSet<>();
	
	public Cart() {
		super();
	}

	public Cart(long id, BigDecimal totalAmount, Set<CartItem> cartItems, LocalUser user) {
		super();
		this.id = id;
		this.totalAmount = totalAmount;
		this.cartItems = cartItems;
		this.user = user;
	}
	


	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItem(Set<CartItem> cartItem) {
		this.cartItems = cartItem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	
	
	public LocalUser getUser() {
		return user;
	}

	public void setUser(LocalUser user) {
		this.user = user;
	}

	public void addItem(CartItem item) {
		this.cartItems.add(item);
		item.setCart(this);
		updateTotalAmount();
	}
	
	public void removeItem(CartItem item) {
		this.cartItems.remove(item);
		updateTotalAmount();
	}
	
	public void updateTotalAmount() {
		this.totalAmount = cartItems.stream().map(item->{
			BigDecimal unitPrice = item.getUnitPrice();
			if(unitPrice == null)
				return BigDecimal.ZERO;
			return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
		}).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
}
