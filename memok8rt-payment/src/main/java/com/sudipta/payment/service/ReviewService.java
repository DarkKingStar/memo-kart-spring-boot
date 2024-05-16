package com.sudipta.payment.service;

import java.util.List;

import com.sudipta.payment.exception.ProductException;
import com.sudipta.payment.modal.Review;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
	
}
