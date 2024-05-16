package com.sudipta.cart.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class JwtUtils {
    
    private static String JWT_URL = "http://localhost:5000/users/jwt/userid";

    public static Long findUserIdByJwt(String jwt,RestTemplate restTemplate) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt.replaceFirst("Bearer ", ""));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity <Long> response = restTemplate.exchange(JWT_URL, HttpMethod.GET, entity, Long.class);
		return response.getBody();
	}
}
