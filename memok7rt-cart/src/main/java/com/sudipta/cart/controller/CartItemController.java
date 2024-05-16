package com.sudipta.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.sudipta.cart.exception.*;
import com.sudipta.cart.model.Cart;
import com.sudipta.cart.model.CartItem;
import com.sudipta.cart.service.CartItemService;
import com.sudipta.cart.service.CartService;
import com.sudipta.cart.utils.JwtUtils;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@RequestMapping("/cart_items")
@Tag(name="Cart Item Management", description = "create cart item delete cart item")
public class CartItemController {
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private CartService cartService;
	@Autowired
	private RestTemplate restTemplate;
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<Cart>deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt) throws CartItemException{
		cartItemService.removeCartItem(cartItemId);
		//--------------------------------------------------------------------------------------
		Long userId = JwtUtils.findUserIdByJwt(jwt,restTemplate);
		//--------------------------------------------------------------------------------------
		Cart cart=cartService.findUserCart(userId);

		return new ResponseEntity<Cart>(cart,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException{
		CartItem updatedCartItem =cartItemService.updateCartItem(cartItemId, cartItem);
		return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
	}
}
