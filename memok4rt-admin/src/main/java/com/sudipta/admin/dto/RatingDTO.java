package com.sudipta.admin.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RatingDTO {
    private Long id;
    private UserDTO user;
    private ProductDTO product;
    private double rating;
    private LocalDateTime createdAt;

}