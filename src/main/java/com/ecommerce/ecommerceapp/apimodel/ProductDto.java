package com.ecommerce.ecommerceapp.apimodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.ecommerceapp.datamodel.Category;
import com.ecommerce.ecommerceapp.datamodel.Product;

public class ProductDto {
	private long id;
	private String name;
	private BigDecimal price;
	private String brand;
	private int inventory;
	private String description;
	private CategoryDto category;
	
	public ProductDto() {
		super();
	}

	

	public ProductDto(long id, String name, BigDecimal price, String brand, int inventory, String description,
			CategoryDto category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.inventory = inventory;
		this.description = description;
		this.category = category;
	}

	public long getId() {
		return id;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}



	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setId(long id) {
		this.id = id;
	}

	

	//FROM ENTITY TO DTO
	public static ProductDto fromEntityToDto(Product product) {
		CategoryDto category = CategoryDto.fromEntityToDto(product.getCategory());
		return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getBrand(), product.getInventory()
				, product.getDescription(), category);			
	}
	
	//FROM LIST ENTITY TO LIST DTO
	public static List<ProductDto> fromEntitylistTodDtolist(List<Product> products){
		List<ProductDto> listProductDto = new ArrayList<>();
		for(Product product : products) {
			ProductDto dtoProdcuts = fromEntityToDto(product);
			listProductDto.add(dtoProdcuts);
		}
		return listProductDto;
	}
	

}
