package com.ecommerce.ecommerceapp.datamodel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.ecommerce.ecommerceapp.apimodel.CategoryDto;
import com.ecommerce.ecommerceapp.apimodel.ProductDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private BigDecimal price;
	@Column(nullable = false)
	private String brand;
	@Column(nullable = false)
	private int inventory = 0;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItem> orderItems = new HashSet<>();
	
	public Product() {
		super();
	}
	
	public Product(String name, String description, String brand, BigDecimal price,  int inventory, Category category) {
		super();
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.category = category;
		this.description = description;
		this.inventory = inventory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String weight) {
		this.brand = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
}
