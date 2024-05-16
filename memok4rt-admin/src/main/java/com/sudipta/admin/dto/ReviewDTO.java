package com.sudipta.admin.dto;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class ReviewDTO {
	private Long id;
	private String review;
	private ProductDTO product;
	private UserDTO user;
	private LocalDateTime createdAt;
}
