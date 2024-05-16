package com.sudipta.payment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sudipta.payment.exception.ProductException;
import com.sudipta.payment.modal.Cart;
import com.sudipta.payment.modal.CartItem;
import com.sudipta.payment.modal.Product;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.repository.CartRepository;
import com.sudipta.payment.request.AddItemRequest;
import com.sudipta.payment.service.CartItemService;
import com.sudipta.payment.service.ProductService;
import com.sudipta.payment.serviceImpl.CartServiceImplementation;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplementationTest {

    @InjectMocks
    private CartServiceImplementation cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemService cartItemService;

    @Mock
    private ProductService productService;

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
    public void testAddCartItem() throws ProductException {
        // Mock data
        Long userId = 1L;
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

        when(cartRepository.findByUserId(any(Long.class))).thenReturn(mockCart);
        when(productService.findProductById(any(Long.class))).thenReturn(mockProduct);
        when(cartItemService.isCartItemExist(any(Cart.class), any(Product.class), any(String.class), any(Long.class)))
                .thenReturn(null);
        when(cartItemService.createCartItem(any(CartItem.class))).thenAnswer(invocation -> {
            CartItem cartItem = invocation.getArgument(0);
            cartItem.setId(1L);
            return cartItem;
        });

        // Call the method
        CartItem addedCartItem = cartService.addCartItem(userId, req);

        // Verify the result
        assertNotNull(addedCartItem);
        assertEquals(req.getQuantity() * mockProduct.getDiscountedPrice(), addedCartItem.getPrice());
        assertEquals(req.getSize(), addedCartItem.getSize());
    }
}

