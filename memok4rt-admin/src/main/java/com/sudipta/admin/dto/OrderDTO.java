package com.sudipta.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sudipta.admin.enums.OrderStatus;

@Data
public class OrderDTO {

    private Long id;

    private String orderId;

    private UserDTO user;

    private List<OrderItemDTO> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    private AddressDTO shippingAddress;

    private PaymentDetailsDTO paymentDetails = new PaymentDetailsDTO();

    private double totalPrice;

    private Integer totalDiscountedPrice;

    private Integer discounte;

    private OrderStatus orderStatus;

    private int totalItem;

    private LocalDateTime createdAt;

}
