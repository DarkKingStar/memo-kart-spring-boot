package com.sudipta.payment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sudipta.payment.modal.OrderItem;
import com.sudipta.payment.repository.OrderItemRepository;
import com.sudipta.payment.serviceImpl.OrderItemServiceImplementation;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplementationTest {

    @InjectMocks
    private OrderItemServiceImplementation orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    public void testCreateOrderItem() {
        // Mock data
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);
        orderItem.setPrice(100);

        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        // Call the method
        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);

        // Verify the result
        assertNotNull(createdOrderItem);
        assertEquals(orderItem.getId(), createdOrderItem.getId());
        assertEquals(orderItem.getQuantity(), createdOrderItem.getQuantity());
        assertEquals(orderItem.getPrice(), createdOrderItem.getPrice());

        // Verify that orderItemRepository.save was called
        verify(orderItemRepository).save(orderItem);
    }
}

