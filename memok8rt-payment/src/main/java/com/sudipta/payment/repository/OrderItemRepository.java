package com.sudipta.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudipta.payment.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
