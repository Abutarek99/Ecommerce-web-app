package com.ecommerce.ecommerceapp.apimodel;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.ecommerceapp.datamodel.OrderItem;

public class OrderitemDto {
	private Long id;
	private ProductDto productDetails;
	private int quantity;
	private BigDecimal price;
	
	public OrderitemDto() {
		super();
	}
	

	public OrderitemDto(Long id, ProductDto productDetails, int quantity, BigDecimal price) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.productDetails = productDetails;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	

	public ProductDto getProduct() {
		return productDetails;
	}


	public void setProduct(ProductDto productDetails) {
		this.productDetails = productDetails;
	}


	//FROM ORDERITEM ENTITY TO ORDERITEM DTO
	public static OrderitemDto fromEntityToDto(OrderItem item) {
		ProductDto productDetails = ProductDto.fromEntityToDto(item.getProduct());
		return new OrderitemDto(item.getId(), productDetails, item.getQuantity(), item.getPrice());
	}
	
	//FROM ORDERITEM ENTITY SET TO ORDERITEM DTO SET
	public static Set<OrderitemDto> fromEntitySetToDtoSet(Set<OrderItem> orderItems){
		return orderItems.stream()
		          .map(item -> fromEntityToDto(item))
		          .collect(Collectors.toSet());       
	}

}
