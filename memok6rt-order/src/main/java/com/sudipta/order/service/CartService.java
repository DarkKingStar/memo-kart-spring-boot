package com.sudipta.order.service;

import com.sudipta.order.dto.Product;
import com.sudipta.order.dto.User;
import com.sudipta.order.dto.Cart;
import com.sudipta.order.dto.CartItem;
import com.sudipta.order.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public CartItem addCartItem(Long userId,AddItemRequest req, Product product);
	
	public Cart findUserCart(Long userId);

}
