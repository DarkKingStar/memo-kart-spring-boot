package com.sudipta.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sudipta.cart.dto.Product;
import com.sudipta.cart.dto.User;
import com.sudipta.cart.model.Cart;
import com.sudipta.cart.model.CartItem;
import com.sudipta.cart.request.AddItemRequest;
import com.sudipta.cart.response.ApiResponse;
import com.sudipta.cart.service.CartService;
import com.sudipta.cart.utils.JwtUtils;
import com.sudipta.cart.utils.ProductUtils;


@RestController
@CrossOrigin("*")
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt){
		//--------------------------------------------------------------------------------------
		Long userId = JwtUtils.findUserIdByJwt(jwt,restTemplate);
		//--------------------------------------------------------------------------------------
		Cart cart=cartService.findUserCart(userId);
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req, 
			@RequestHeader("Authorization") String jwt){
		//--------------------------------------------------------------------------------------
		Long userId = JwtUtils.findUserIdByJwt(jwt, restTemplate);
		Product product = ProductUtils.findProductById(jwt, req.getProductId(), restTemplate);
		//--------------------------------------------------------------------------------------
		CartItem item = cartService.addCartItem(userId, req, product);
		return new ResponseEntity<>(item,HttpStatus.OK);
	}

	@PutMapping("/create")
	public ResponseEntity<ApiResponse> createCartHandler(@RequestBody User user){
		cartService.createCart(user);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Cart Created Successfully",true),HttpStatus.OK);
	}
}
