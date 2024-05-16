package com.sudipta.cart;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sudipta.cart.dto.Product;
import com.sudipta.cart.dto.User;
import com.sudipta.cart.model.Cart;
import com.sudipta.cart.model.CartItem;
import com.sudipta.cart.repository.CartRepository;
import com.sudipta.cart.request.AddItemRequest;
import com.sudipta.cart.service.CartItemService;
import com.sudipta.cart.serviceImpl.CartServiceImplementation;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplementationTest {

    @InjectMocks
    private CartServiceImplementation cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemService cartItemService;



    @Test
    public void testCreateCart() {
        // Mock data
        User user = new User();
        Cart mockCart = new Cart();
        mockCart.setUser(user);

        when(cartRepository.save(any(Cart.class))).thenReturn(mockCart);

        // Call the method
        Cart createdCart = cartService.createCart(user);

        // Verify the result
        assertNotNull(createdCart);
        assertEquals(user, createdCart.getUser());
    }

    @Test
    public void testFindUserCart() {
        // Mock data
        Long userId = 1L;
        Cart mockCart = new Cart();
        mockCart.setId(1L);
        mockCart.setUser(new User());
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setPrice(100);
        cartItem.setDiscountedPrice(90);
        cartItem.setQuantity(2);
        cartItems.add(cartItem);
       // mockCart.setCartItems(cartItems);

        when(cartRepository.findByUserId(any(Long.class))).thenReturn(mockCart);

        // Call the method
        Cart foundCart = cartService.findUserCart(userId);

        // Verify the result
        // assertNull(foundCart);
        // assertNotEquals(mockCart.getTotalPrice(), foundCart.getTotalPrice());
        // assertEquals(mockCart.getTotalDiscountedPrice(), foundCart.getTotalDiscountedPrice());
        // assertEquals(mockCart.getTotalItem(), foundCart.getTotalItem());
        // assertEquals(mockCart.getDiscounte(), foundCart.getDiscounte());
        // assertEquals(mockCart.getTotalItem(), foundCart.getTotalItem());
    }

    @Test
    public void testAddCartItem()  {
        // Mock data
        AddItemRequest req = new AddItemRequest();
        req.setProductId(1L);
        req.setSize("M");
        req.setQuantity(2);

        Cart mockCart = new Cart();
        mockCart.setId(1L);
        mockCart.setUser(new User());

        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setDiscountedPrice(50);

        //when(cartRepository.findByUserId(any(Long.class))).thenReturn(mockCart);
        //when(cartItemService.isCartItemExist(any(Cart.class), any(Product.class), any(String.class), any(Long.class)))
              //  .thenReturn(null);
        // //when(cartItemService.createCartItem(any(CartItem.class))).thenAnswer(invocation -> {
        //     CartItem cartItem = invocation.getArgument(0);
        //     cartItem.setId(1L);
        //     return cartItem;
        // });



        // Call the method
        // CartItem addedCartItem = cartService.addCartItem(userId, req, product);

        // // Verify the result
        // assertNotNull(addedCartItem);
        // assertEquals(req.getQuantity() * mockProduct.getDiscountedPrice(), addedCartItem.getPrice());
        // assertEquals(req.getSize(), addedCartItem.getSize());
    }
}

