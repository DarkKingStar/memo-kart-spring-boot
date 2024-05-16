package com.sudipta.admin.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sudipta.admin.dto.UserDTO;
import com.sudipta.admin.exception.UserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;




@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminUserController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	final String ROOT_URI = "http://localhost:5000"; // auth microservice

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers(@RequestHeader("Authorization") String jwt) throws UserException{
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<UserDTO[]> response = restTemplate.exchange(ROOT_URI+"/users/allusers", HttpMethod.GET, entity, UserDTO[].class);
		
		List<UserDTO> userList = Arrays.asList(response.getBody());
		
		return ResponseEntity.ok(userList);
	}

}
