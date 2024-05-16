package com.sudipta.order.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudipta.order.dto.Product;
import com.sudipta.order.exception.CartItemException;
import com.sudipta.order.dto.Cart;
import com.sudipta.order.dto.CartItem;
import com.sudipta.order.repository.CartItemRepository;
import com.sudipta.order.service.CartItemService;


@Service
public class CartItemServiceImplementation implements CartItemService {
	@Autowired
	private CartItemRepository cartItemRepository;
	
	

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem( Long id, CartItem cartItem) throws CartItemException {
		
		CartItem item=findCartItemById(id);

			
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());
			
			return cartItemRepository.save(item);
		
		
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);
		
		return cartItem;
	}
	
	

	@Override
	public void removeCartItem(Long cartItemId) throws CartItemException{
		CartItem cartItem=findCartItemById(cartItemId);
		cartItemRepository.deleteById(cartItem.getId());
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cartItem not found with id : "+cartItemId);
	}

}
