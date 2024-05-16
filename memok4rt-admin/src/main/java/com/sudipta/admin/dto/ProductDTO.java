package com.sudipta.admin.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int discountPersent;
    private int quantity;
    private String brand;
    private String color;
    private Set<SizeDTO> sizes=new HashSet<>();
    private String imageUrl;
    private List<RatingDTO>ratings=new ArrayList<>();
    private List<ReviewDTO>reviews=new ArrayList<>();
    private int numRatings;
    private CategoryDTO category;
    private LocalDateTime createdAt;
}
