package com.sudipta.payment.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudipta.payment.exception.ProductException;
import com.sudipta.payment.modal.Product;
import com.sudipta.payment.modal.Rating;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.repository.RatingRepository;
import com.sudipta.payment.request.RatingRequest;
import com.sudipta.payment.service.ProductService;
import com.sudipta.payment.service.RatingServices;

@Service
public class RatingServiceImplementation implements RatingServices{
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private ProductService productService;
	
	

	@Override
	public Rating createRating(RatingRequest req,User user) throws ProductException {
		
		Product product=productService.findProductById(req.getProductId());
		
		Rating rating=new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		// TODO Auto-generated method stub
		return ratingRepository.getAllProductsRating(productId);
	}
	
	

}
