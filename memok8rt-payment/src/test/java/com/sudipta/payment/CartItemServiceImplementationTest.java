package com.sudipta.payment;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sudipta.payment.exception.CartItemException;
import com.sudipta.payment.exception.UserException;
import com.sudipta.payment.modal.CartItem;
import com.sudipta.payment.modal.Product;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.repository.CartItemRepository;
import com.sudipta.payment.service.UserService;
import com.sudipta.payment.serviceImpl.CartItemServiceImplementation;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceImplementationTest {

    @InjectMocks
    private CartItemServiceImplementation cartItemService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserService userService;

    @Test
    public void testCreateCartItem() {
        // Mock data
        CartItem cartItem = new CartItem();
        Product product = new Product();
        product.setPrice(100); // Set product price for testing
        product.setDiscountedPrice(90); // Set discounted price for testing
        cartItem.setProduct(product);

        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Call the method
        CartItem createdCartItem = cartItemService.createCartItem(cartItem);

        // Verify the result
        assertNotNull(createdCartItem);
        assertEquals(1, createdCartItem.getQuantity());
        assertEquals(100, createdCartItem.getPrice());
        assertEquals(90, createdCartItem.getDiscountedPrice());
    }

    @Test
    public void testUpdateCartItem() throws CartItemException, UserException {
        // Mock data
        Long userId = 1L;
        Long itemId = 1L;
        CartItem cartItem = new CartItem();
        cartItem.setId(itemId);
        cartItem.setUserId(userId);
        cartItem.setProduct(new Product());
        User user = new User();
        user.setId(userId);

        when(cartItemRepository.findById(any(Long.class))).thenReturn(Optional.of(cartItem));
        when(userService.findUserById(any(Long.class))).thenReturn(user);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Call the method
        CartItem updatedCartItem = cartItemService.updateCartItem(userId, itemId, cartItem);

        // Verify the result
        assertNotNull(updatedCartItem);
        assertEquals(cartItem.getId(), updatedCartItem.getId());
    }

    @Test
    public void testRemoveCartItem() throws CartItemException, UserException {
        // Mock data
        Long userId = 1L;
        Long cartItemId = 1L;
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        cartItem.setUserId(userId);
        User user = new User();
        user.setId(userId);

        when(cartItemRepository.findById(any(Long.class))).thenReturn(Optional.of(cartItem));
        when(userService.findUserById(any(Long.class))).thenReturn(user);

        // Call the method
        cartItemService.removeCartItem(userId, cartItemId);

        // Verify that cartItemRepository.deleteById was called
        verify(cartItemRepository, times(1)).deleteById(cartItemId);
    }

    @Test
    public void testFindCartItemById_Success() throws CartItemException {
        // Mock data
        Long cartItemId = 1L;
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);

        when(cartItemRepository.findById(any(Long.class))).thenReturn(Optional.of(cartItem));

        // Call the method
        CartItem foundCartItem = cartItemService.findCartItemById(cartItemId);

        // Verify the result
        assertNotNull(foundCartItem);
        assertEquals(cartItemId, foundCartItem.getId());
    }

    @Test
    public void testFindCartItemById_CartItemNotFound() {
        // Mock cartItemRepository to return empty optional
        when(cartItemRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Call the method and verify that CartItemException is thrown
        assertThrows(CartItemException.class, () -> cartItemService.findCartItemById(100L));
    }
}
