package com.sudipta.admin.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderItemDTO {
	

	private Long id;

	private OrderDTO order;
	

	private ProductDTO product;
	
	private String size;
	
	private int quantity;
	
	private Integer price;
	
	private Integer discountedPrice;
	
	private Long userId;
	
	private LocalDateTime deliveryDate;
}
