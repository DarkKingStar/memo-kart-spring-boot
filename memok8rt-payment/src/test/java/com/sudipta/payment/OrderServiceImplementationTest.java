package com.sudipta.payment;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sudipta.payment.exception.OrderException;
import com.sudipta.payment.modal.Address;
import com.sudipta.payment.modal.Cart;
import com.sudipta.payment.modal.CartItem;
import com.sudipta.payment.modal.Order;
import com.sudipta.payment.modal.OrderItem;
import com.sudipta.payment.modal.PaymentDetails;
import com.sudipta.payment.modal.Product;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.repository.AddressRepository;
import com.sudipta.payment.repository.OrderItemRepository;
import com.sudipta.payment.repository.OrderRepository;
import com.sudipta.payment.repository.UserRepository;
import com.sudipta.payment.service.CartService;
import com.sudipta.payment.serviceImpl.OrderServiceImplementation;
import com.sudipta.payment.user.domain.OrderStatus;
import com.sudipta.payment.user.domain.PaymentStatus;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplementationTest {

    @InjectMocks
    private OrderServiceImplementation orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartService cartService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    // @Test
    // public void testCreateOrder() throws OrderException {
    //     // Mock data
    //     User mockUser = new User();
    //     mockUser.setId(1L);
    //     mockUser.setEmail("test@example.com");

    //     Address mockAddress = new Address();
    //     mockAddress.setId(1L);
    //     //mockAddress.setStreet("123 Test Street");

    //     Cart mockCart = new Cart();
    //     mockCart.setId(1L);

    //     CartItem mockCartItem = new CartItem();
    //     mockCartItem.setId(1L);
    //     mockCartItem.setProduct(new Product());
    //     mockCartItem.setQuantity(2);
    //     mockCartItem.setPrice(100);
    //     mockCartItem.setDiscountedPrice(90);

    //     List<CartItem> cartItems = new ArrayList<>();
    //     cartItems.add(mockCartItem);
    //     //mockCart.setCartItems(cartItems);

    //     when(cartService.findUserCart(mockUser.getId())).thenReturn(mockCart);
    //     when(addressRepository.save(any(Address.class))).thenReturn(mockAddress);
    //     when(userRepository.save(any(User.class))).thenReturn(mockUser);
    //     when(orderItemRepository.save(any(OrderItem.class))).thenReturn(new OrderItem());
    //     when(orderRepository.save(any(Order.class))).thenReturn(new Order());

    //     // Call the method
    //     Order createdOrder = orderService.createOrder(mockUser, mockAddress);

    //     // Verify the result
    //     assertNotNull(createdOrder);
    //    // assertEquals(mockUser, createdOrder.getUser());
    //     assertEquals(mockAddress, createdOrder.getShippingAddress());
    //     assertEquals(OrderStatus.PENDING, createdOrder.getOrderStatus());
    //     assertEquals(PaymentStatus.PENDING, createdOrder.getPaymentDetails().getStatus());
    //     assertEquals(1, createdOrder.getOrderItems().size());
    //     assertEquals(100, createdOrder.getTotalPrice());
    //     assertEquals(90, createdOrder.getTotalDiscountedPrice());
    //     assertEquals(10, createdOrder.getDiscounte());
    //     assertEquals(2, createdOrder.getTotalItem());
    //     assertNotNull(createdOrder.getOrderDate());
    //     assertNotNull(createdOrder.getCreatedAt());
    // }

    @Test
    public void testPlacedOrder() throws OrderException {
        Long orderId = 1L;
        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        mockOrder.setOrderStatus(OrderStatus.PENDING);
        mockOrder.setPaymentDetails(new PaymentDetails());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        Order placedOrder = orderService.placedOrder(orderId);

        assertNotNull(placedOrder);
        assertEquals(OrderStatus.PLACED, placedOrder.getOrderStatus());
        assertEquals(PaymentStatus.COMPLETED, placedOrder.getPaymentDetails().getStatus());
    }

    

}
