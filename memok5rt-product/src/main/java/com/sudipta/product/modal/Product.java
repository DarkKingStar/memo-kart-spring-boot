package com.sudipta.product.modal;

import java.time.LocalDateTime;
import java.util.*;

import com.sudipta.product.dto.Rating;
import com.sudipta.product.dto.Review;
import com.sudipta.product.dto.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "discounted_price")
    private int discountedPrice;
    
    @Column(name="discount_persent")
    private int discountPersent;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

    @Embedded
    @ElementCollection
    @Column(name = "sizes")
    private Set<Size> sizes=new HashSet<>();

    @Column(name = "image_url",length = 2000)
    private String imageUrl;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Rating>ratings=new ArrayList<>();
    
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review>reviews=new ArrayList<>();

    @Column(name = "num_ratings")
    private int numRatings;
    

    @ManyToOne()
    @JoinColumn(name="category_id")
    private Category category;
    
    private LocalDateTime createdAt;
   
}
