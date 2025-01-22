package com.ecommerce.ecommerceapp.service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerceapp.datamodel.Cart;
import com.ecommerce.ecommerceapp.datamodel.CartItem;
import com.ecommerce.ecommerceapp.datamodel.LocalUser;
import com.ecommerce.ecommerceapp.datamodel.Product;
import com.ecommerce.ecommerceapp.exception.ResourceNotFoundException;
import com.ecommerce.ecommerceapp.repository.CartRepository;
import com.ecommerce.ecommerceapp.repository.CartitemRepository;
import com.ecommerce.ecommerceapp.repository.UserRepository;

@Service
public class CartitemService{
	@Autowired
	private CartitemRepository cartitemRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CartRepository cartRepository;
	
	//ADD CARTITEM
	public void addItemToCart(long cart_id, long product_id, int quantity) {
	    /*	LocalUser user = userService.getUserById(user_id);
		Cart cart = user.getCart();
		if(cart == null) {
			cart = new Cart();
			cart.setUser(user);
			cartRepository.save(cart);
			user.setCart(cart);
			userRepository.save(user);
		}*/
		
		Cart cart = cartService.getCartById(cart_id);
		Product product =productService.getProductById(product_id);
		CartItem cartItem = cart.getCartItems()
				.stream()
				.filter(item -> item.getProduct().getId() == product_id)
				.findFirst().orElse(new CartItem());
		
		if(cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
			cartItem.setTotalPrice();
			cart.addItem(cartItem);
		}
		else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			cartItem.setTotalPrice();
			cart.updateTotalAmount();
		}
		cartitemRepository.save(cartItem);
		cartRepository.save(cart);
	}
	
	//REMOVE ITEM FROM CART
	public void removeItemFromCart(long cart_id, long product_id) {
		Cart cart = cartService.getCartById(cart_id);
		CartItem itemToRemove = cart.getCartItems()
				.stream()
				.filter(item-> item.getProduct().getId() == product_id)
				.findFirst().orElseThrow(() -> new ResourceNotFoundException("The Item you are trying to update was not found"));
		cart.removeItem(itemToRemove);
		cartRepository.save(cart);
	}
	
	//UPDATE QUANTITY IN AN ITEM
	public void updateQuantityItem(long cart_id, Long product_id, int quantity) {
		 Cart cart = cartService.getCartById(cart_id);
		 cart.getCartItems()
		        .stream()
		        .filter(item -> item.getProduct().getId()== product_id)
		        .findFirst()
		        .ifPresentOrElse(item ->{
		        	item.setQuantity(quantity);
		        	item.setTotalPrice();
		        },
		        	()-> {throw new ResourceNotFoundException("The item you are trying to update the quantity for "
		        			+ "is not present in the system");}
		        );
		BigDecimal totalAmount = cart.getCartItems()
				.stream()
				.map(CartItem :: getTotalPrice)
				.reduce(BigDecimal.ZERO, BigDecimal :: add);
		cart.setTotalAmount(totalAmount);
		cartRepository.save(cart);       
	}
	
	//Get an item from the cart
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = cartRepository.findById(cartId)
		              .orElseThrow(() -> new ResourceNotFoundException("The cart you are trying to get the item from is not present."));
		return cart.getCartItems()
	               .stream()
	               .filter(item -> item.getProduct().getId()==productId)
	               .findFirst()
	               .orElseThrow(() -> new ResourceNotFoundException("The specified item was not found in the system"));
	}
	
	//"Retrieve all items belonging to a specific cart."
	public Set<CartItem> getItemsByCartid(Long cart_id){
		Set<CartItem> cartItem = cartitemRepository.findByCartId(cart_id);
		if(cartItem.isEmpty()) {
			throw new ResourceNotFoundException("No items found for cart ID "+ cart_id);
		}
		else
			return cartItem;
	}
}
