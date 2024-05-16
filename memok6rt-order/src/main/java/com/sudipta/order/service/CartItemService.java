package com.sudipta.order.service;

import com.sudipta.order.dto.Product;
import com.sudipta.order.exception.CartItemException;
import com.sudipta.order.dto.Cart;
import com.sudipta.order.dto.CartItem;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem( Long id,CartItem cartItem) throws CartItemException;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	public void removeCartItem(Long cartItemId) throws CartItemException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
