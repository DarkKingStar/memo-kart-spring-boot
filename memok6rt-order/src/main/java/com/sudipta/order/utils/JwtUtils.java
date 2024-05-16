package com.sudipta.order.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.sudipta.order.dto.User;

public class JwtUtils {
    
    private static String JWT_URL = "http://localhost:5000/users/jwt";


    public static Long findUserIdByJwt(String jwt,RestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt.replaceFirst("Bearer ", ""));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity <Long> response = restTemplate.exchange(JWT_URL+ "/userid", HttpMethod.GET, entity, Long.class);
		return response.getBody();
	}
	public static User findUserByJwt(String jwt,RestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt.replaceFirst("Bearer ", ""));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity <User> response = restTemplate.exchange(JWT_URL, HttpMethod.GET, entity, User.class);

		return response.getBody();
	}
}
