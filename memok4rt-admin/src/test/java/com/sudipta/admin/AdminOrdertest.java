package com.sudipta.admin;

import com.sudipta.admin.controller.AdminOrderController;
import com.sudipta.admin.dto.OrderDTO;
import com.sudipta.admin.exception.OrderException;
import com.sudipta.admin.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminOrdertest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AdminOrderController adminOrderController;

    private final String jwtToken = "your_jwt_token";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrders() throws OrderException {
        // Mock the response from the restTemplate
        OrderDTO[] mockOrdersArray = {new OrderDTO(), new OrderDTO()};
        List<OrderDTO> mockOrdersList = Arrays.asList(mockOrdersArray);
        ResponseEntity<OrderDTO[]> mockResponseEntity = new ResponseEntity<>(mockOrdersArray, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("http://localhost:5002/orders/allorders"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(OrderDTO[].class)))
                .thenReturn(mockResponseEntity);

        // Call the controller method
        ResponseEntity<List<OrderDTO>> response = adminOrderController.getAllOrders(jwtToken);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrdersList, response.getBody());
    }

    @Test
    public void testConfirmedOrderHandler() throws OrderException {
        // Mock the response from the restTemplate
        OrderDTO mockOrder = new OrderDTO();
        ResponseEntity<OrderDTO> mockResponseEntity = new ResponseEntity<>(mockOrder, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(
                eq("http://localhost:5002/orders/123/confirmed"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(OrderDTO.class)))
                .thenReturn(mockResponseEntity);

        // Call the controller method
        ResponseEntity<OrderDTO> response = adminOrderController.ConfirmedOrderHandler(123L, jwtToken);

        // Verify the response
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockOrder, response.getBody());
    }

    @Test
    public void testShippedOrderHandler() throws OrderException {
        // Mock the response from the restTemplate
        OrderDTO mockOrder = new OrderDTO();
        ResponseEntity<OrderDTO> mockResponseEntity = new ResponseEntity<>(mockOrder, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(
                eq("http://localhost:5002/orders/123/ship"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(OrderDTO.class)))
                .thenReturn(mockResponseEntity);

        // Call the controller method
        ResponseEntity<OrderDTO> response = adminOrderController.shippedOrderHandler(123L, jwtToken);

        // Verify the response
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockOrder, response.getBody());
    }

    @Test
    public void testDeliveredOrderHandler() throws OrderException {
        // Mock the response from the restTemplate
        OrderDTO mockOrder = new OrderDTO();
        ResponseEntity<OrderDTO> mockResponseEntity = new ResponseEntity<>(mockOrder, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(
                eq("http://localhost:5002/orders/123/deliver"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(OrderDTO.class)))
                .thenReturn(mockResponseEntity);

        // Call the controller method
        ResponseEntity<OrderDTO> response = adminOrderController.deliveredOrderHandler(123L, jwtToken);

        // Verify the response
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockOrder, response.getBody());
    }

    @Test
    public void testCanceledOrderHandler() throws OrderException {
        // Mock the response from the restTemplate
        OrderDTO mockOrder = new OrderDTO();
        ResponseEntity<OrderDTO> mockResponseEntity = new ResponseEntity<>(mockOrder, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(
                eq("http://localhost:5002/orders/123/cancel"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(OrderDTO.class)))
                .thenReturn(mockResponseEntity);

        // Call the controller method
        ResponseEntity<OrderDTO> response = adminOrderController.canceledOrderHandler(123L, jwtToken);

        // Verify the response
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockOrder, response.getBody());
    }

    @Test
    public void testDeleteOrderHandler() throws OrderException {
        // Mock the response from the restTemplate
        ApiResponse mockApiResponse = new ApiResponse("Order deleted successfully", true);
        ResponseEntity<ApiResponse> mockResponseEntity = new ResponseEntity<>(mockApiResponse, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(
                eq("http://localhost:5002/orders/123/delete"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(ApiResponse.class)))
                .thenReturn(mockResponseEntity);

        // Call the controller method
        ResponseEntity<ApiResponse> response = adminOrderController.deleteOrderHandler(123L, jwtToken);

        // Verify the response
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockApiResponse, response.getBody());
    }
}

