package com.sudipta.cart.service;

import com.sudipta.cart.dto.Product;
import com.sudipta.cart.exception.CartItemException;
import com.sudipta.cart.model.Cart;
import com.sudipta.cart.model.CartItem;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem( Long id,CartItem cartItem) throws CartItemException;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	public void removeCartItem(Long cartItemId) throws CartItemException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}
