package com.sudipta.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudipta.order.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
