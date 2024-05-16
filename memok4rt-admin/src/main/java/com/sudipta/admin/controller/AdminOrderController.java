package com.sudipta.admin.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sudipta.admin.dto.OrderDTO;
import com.sudipta.admin.exception.OrderException;
import com.sudipta.admin.response.ApiResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;


@RestController
@CrossOrigin("*")
@RequestMapping("/admin/orders")
public class AdminOrderController {

	@Autowired
	private RestTemplate restTemplate;

	final String ROOT_URI = "http://localhost:5002"; //order microservice

	@GetMapping("/")
	public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestHeader("Authorization") String jwt) throws OrderException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<OrderDTO[]> response = restTemplate.exchange(
			ROOT_URI+"/orders/allorders", HttpMethod.GET, entity, OrderDTO[].class);

		List<OrderDTO> orders = Arrays.asList(response.getBody());
		
		return ResponseEntity.ok(orders);
	}	

	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<OrderDTO> ConfirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<OrderDTO> response = restTemplate.exchange(
				ROOT_URI+"/orders/"+orderId+"/confirmed", HttpMethod.PUT, entity, OrderDTO.class);

		OrderDTO order = response.getBody();
		
		return new ResponseEntity<OrderDTO>(order, HttpStatus.ACCEPTED);
	}


	@PutMapping("/{orderId}/ship")
	public ResponseEntity<OrderDTO> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<OrderDTO> response = restTemplate.exchange(
				ROOT_URI+"/orders/"+orderId+"/ship", HttpMethod.PUT, entity, OrderDTO.class);

		OrderDTO order = response.getBody();
		
		return new ResponseEntity<OrderDTO>(order, HttpStatus.ACCEPTED);
	}


	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<OrderDTO> deliveredOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<OrderDTO> response = restTemplate.exchange(
				ROOT_URI+"/orders/"+orderId+"/deliver", HttpMethod.PUT, entity, OrderDTO.class);

		OrderDTO order = response.getBody();
		
		return new ResponseEntity<OrderDTO>(order, HttpStatus.ACCEPTED);
	}


	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<OrderDTO> canceledOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<OrderDTO> response = restTemplate.exchange(
				ROOT_URI+"/orders/"+orderId+"/cancel", HttpMethod.PUT, entity, OrderDTO.class);

		OrderDTO order = response.getBody();
		
		return new ResponseEntity<OrderDTO>(order, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		
		System.out.println("order id --- " + orderId);

		ResponseEntity<ApiResponse> response = restTemplate.exchange(
				ROOT_URI+"/orders/"+orderId+"/delete", HttpMethod.PUT, entity, ApiResponse.class);

		ApiResponse res = response.getBody();
		
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}
}
