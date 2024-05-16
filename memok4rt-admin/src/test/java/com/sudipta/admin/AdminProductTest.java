package com.sudipta.admin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudipta.admin.controller.AdminProductController;
import com.sudipta.admin.dto.ProductDTO;
import com.sudipta.admin.request.CreateProductRequest;
import com.sudipta.admin.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminProductTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AdminProductController adminProductController;

    private final String jwtToken = "your_jwt_token";
    private final String proUrl = "http://localhost:5005/prod/admin/products";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProductHandler() {
        CreateProductRequest request = new CreateProductRequest();
        ProductDTO mockProduct = new ProductDTO(); // Create a mock ProductDTO for testing

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        HttpEntity<CreateProductRequest> entity = new HttpEntity<>(request, headers);

        when(restTemplate.exchange(eq(proUrl + "/"), eq(HttpMethod.POST), any(HttpEntity.class), eq(ProductDTO.class)))
                .thenReturn(new ResponseEntity<>(mockProduct, HttpStatus.ACCEPTED));

        ResponseEntity<ProductDTO> response = adminProductController.createProductHandler(jwtToken, request);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    public void testDeleteProductHandler() {
        long productId = 123L;
        ApiResponse mockApiResponse = new ApiResponse("Product deleted successfully", true);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        HttpEntity<Long> entity = new HttpEntity<>(productId, headers);

        when(restTemplate.exchange(eq(proUrl + "/" + productId + "/delete"), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(ApiResponse.class)))
                .thenReturn(new ResponseEntity<>(mockApiResponse, HttpStatus.ACCEPTED));

        ResponseEntity<ApiResponse> response = adminProductController.deleteProductHandler(jwtToken, productId);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockApiResponse, response.getBody());
    }

    @Test
    public void testFindAllProduct() {
        List<ProductDTO> mockProductList = new ArrayList<>(); // Create a list of mock ProductDTO for testing

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        when(restTemplate.exchange(eq(proUrl + "/all"), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(mockProductList, HttpStatus.OK));

        ResponseEntity<List<ProductDTO>> response = adminProductController.findAllProduct(jwtToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProductList, response.getBody());
    }

    
}

