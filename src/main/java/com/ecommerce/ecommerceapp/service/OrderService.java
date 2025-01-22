package com.ecommerce.ecommerceapp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerceapp.apimodel.OrderResponse;
import com.ecommerce.ecommerceapp.apimodel.OrderResponseWithoutUserDetails;
import com.ecommerce.ecommerceapp.datamodel.Cart;
import com.ecommerce.ecommerceapp.datamodel.OrderItem;
import com.ecommerce.ecommerceapp.datamodel.Product;
import com.ecommerce.ecommerceapp.datamodel.WebOrder;
import com.ecommerce.ecommerceapp.enums.OrderStatus;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.repository.CartRepository;
import com.ecommerce.ecommerceapp.repository.OrderRepository;
import com.ecommerce.ecommerceapp.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired 
	private CartService cartService;
	
	
	public OrderResponse placeOrder(Long userId) {
		Cart cart = Optional.ofNullable(cartRepository.findByUserId(userId))
		                   .orElseThrow(()-> new ResourceNotFoundException("Your cart is empty, so you cannot place an order"));
		WebOrder newOrder = new WebOrder();
		newOrder.setUser(cart.getUser());
		newOrder.setOrderStatus(OrderStatus.PENDING);
		newOrder.setOrderDate(LocalDate.now());
		List<OrderItem> orderItemList = createOrderItems(newOrder, cart);
	    newOrder.setOrderItems(new HashSet<>(orderItemList));
	    newOrder.setTotlaAmount(calculateOrderTotalAmount(orderItemList));
	    WebOrder order = orderRepository.save(newOrder);
	    OrderResponse orderMade =  OrderResponse.fromEntityToDto(order);
	    cartService.clearCart(cart.getId());
	    return orderMade;
	}
	
	
	 public List<OrderItem> createOrderItems(WebOrder order, Cart cart) {
	        return  cart.getCartItems().stream().map(cartItem -> {
	            Product product = cartItem.getProduct();
	            product.setInventory(product.getInventory() - cartItem.getQuantity());
	            productRepository.save(product);
	            return  new OrderItem(  cartItem.getProduct(), cartItem.getQuantity(), cartItem.getUnitPrice(), order);
	        }).toList();
	 }
	   
	 public BigDecimal calculateOrderTotalAmount(List <OrderItem> orderItems) {
		  return orderItems.stream()
		         .map(item-> item.getPrice()
		        		 .multiply(new BigDecimal(item.getQuantity())))
		         .reduce(BigDecimal.ZERO, BigDecimal::add);
	 }
	   
	  
	 public List<OrderResponseWithoutUserDetails> getUserOrders(Long userId) {
	         List<WebOrder> allOrders = orderRepository.findByUserId(userId)
				  .orElseThrow(()-> new ResourceNotFoundException("No orders are associated with this user"));
		  return OrderResponseWithoutUserDetails.fromListEntityToDtoEntity(allOrders);
	 }
	
	 
	public OrderResponse getOrderById(Long orderId) {
	     return orderRepository.findById(orderId)
	    		 .map(OrderResponse :: fromEntityToDto)
	    		 .orElseThrow(()-> new ResourceNotFoundException("The specified order was not found in the system"));
	}
}
