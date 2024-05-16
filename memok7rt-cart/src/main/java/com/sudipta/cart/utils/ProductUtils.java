package com.sudipta.cart.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.sudipta.cart.dto.Product;

public class ProductUtils {
    
    private static String JWT_URL = "http://localhost:5005/all/products/id";

    public static Product findProductById(String jwt,Long id,RestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt.replaceFirst("Bearer ", ""));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity <Product> response = restTemplate.exchange(JWT_URL+"/"+id, HttpMethod.GET, entity, Product.class);
		return response.getBody();
	}
}
