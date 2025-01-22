package com.ecommerce.ecommerceapp.apimodel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.ecommerce.ecommerceapp.datamodel.Cart;
import com.ecommerce.ecommerceapp.datamodel.CartItem;
import com.ecommerce.ecommerceapp.datamodel.Product;

public class CartItemDto {
	private Long id;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private ProductDto product;
	
	public CartItemDto() {
		super();
	}

	public CartItemDto(Long id, ProductDto product,  Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	//FROM ENTITY CARTITEM TO DTO CARTITEM
	public static CartItemDto fromEntityToDto(CartItem cartItem) {
		ProductDto productDetails = ProductDto.fromEntityToDto(cartItem.getProduct());
		return new CartItemDto(cartItem.getId(), productDetails,
				cartItem.getQuantity(), cartItem.getUnitPrice(), cartItem.getTotalPrice()
		);	
	}
	
	public static Set<CartItemDto> fromEntityListToDtoList(Set<CartItem> items){
		Set<CartItemDto> listItemsDto = new HashSet<>();
		for(CartItem item : items) {
			CartItemDto itemsDto = fromEntityToDto(item);
			listItemsDto.add(itemsDto);
		}
		return listItemsDto;
	}

}
