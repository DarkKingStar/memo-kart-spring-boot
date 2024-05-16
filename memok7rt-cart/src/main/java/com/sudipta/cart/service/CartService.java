package com.sudipta.cart.service;

import com.sudipta.cart.dto.Product;
import com.sudipta.cart.dto.User;
import com.sudipta.cart.model.Cart;
import com.sudipta.cart.model.CartItem;
import com.sudipta.cart.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public CartItem addCartItem(Long userId,AddItemRequest req, Product product);
	
	public Cart findUserCart(Long userId);

}
