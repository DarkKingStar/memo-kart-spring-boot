package com.sudipta.payment.service;

import java.util.List;

import com.sudipta.payment.exception.ProductException;
import com.sudipta.payment.modal.Rating;
import com.sudipta.payment.modal.User;
import com.sudipta.payment.request.RatingRequest;

public interface RatingServices {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}
