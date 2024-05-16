package com.sudipta.payment.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudipta.payment.exception.ProductException;
import com.sudipta.payment.modal.Product;
import com.sudipta.payment.modal.Review;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.repository.ProductRepository;
import com.sudipta.payment.repository.ReviewRepository;
import com.sudipta.payment.request.ReviewRequest;
import com.sudipta.payment.service.ProductService;
import com.sudipta.payment.service.ReviewService;

@Service
public class ReviewServiceImplementation implements ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;
	
	

	@Override
	public Review createReview(ReviewRequest req,User user) throws ProductException {
		// TODO Auto-generated method stub
		Product product=productService.findProductById(req.getProductId());
		Review review=new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
//		product.getReviews().add(review);
		productRepository.save(product);
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
