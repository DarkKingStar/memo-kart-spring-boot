package com.sudipta.payment.service;

import com.sudipta.payment.exception.ProductException;
import com.sudipta.payment.modal.Cart;
import com.sudipta.payment.modal.CartItem;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public CartItem addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);

}
