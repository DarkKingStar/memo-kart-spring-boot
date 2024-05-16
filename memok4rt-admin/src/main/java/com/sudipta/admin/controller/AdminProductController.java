package com.sudipta.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sudipta.admin.dto.ProductDTO;
import com.sudipta.admin.request.CreateProductRequest;
import com.sudipta.admin.response.ApiResponse;

import org.springframework.http.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin/products")
public class AdminProductController {
    @Autowired
    private RestTemplate restTemplate;

    private String PRO_URL = "http://localhost:5005/prod/admin/products";//product microservice
	
	@PostMapping("/")
	public ResponseEntity<ProductDTO> createProductHandler(@RequestHeader("Authorization") String jwt ,@RequestBody CreateProductRequest req){
        HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		HttpEntity<CreateProductRequest> entity = new HttpEntity<>(req, headers);
        ProductDTO createdProduct =  restTemplate.exchange(
			PRO_URL+"/", HttpMethod.POST, entity, ProductDTO.class).getBody();
		return new ResponseEntity<ProductDTO>(createdProduct,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProductHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long productId){
        HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", jwt);
		HttpEntity<Long> entity = new HttpEntity<>(productId, headers);
        ResponseEntity<ApiResponse> res=restTemplate.exchange(
            PRO_URL+"/"+productId+"/delete", HttpMethod.DELETE, entity, ApiResponse.class);
		return new ResponseEntity<ApiResponse>(res.getBody(),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductDTO>> findAllProduct(@RequestHeader("Authorization") String jwt){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<ProductDTO>> response = restTemplate.exchange(
            PRO_URL+"/all", HttpMethod.GET, entity, new ParameterizedTypeReference<List<ProductDTO>>() {});
        List<ProductDTO> products = response.getBody();
        
		return new ResponseEntity<List<ProductDTO>>(products,HttpStatus.OK);
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<ProductDTO>> recentlyAddedProduct(@RequestHeader("Authorization") String jwt){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        List<ProductDTO> products = restTemplate.exchange(
            PRO_URL+"/recent", HttpMethod.GET, entity, new ParameterizedTypeReference<List<ProductDTO>>() {}).getBody();  
		return new ResponseEntity<List<ProductDTO>>(products,HttpStatus.OK);
	}
	
	
	@PutMapping("/{productId}/update")
	public ResponseEntity<ProductDTO> updateProductHandler(@RequestHeader("Authorization") String jwt,@RequestBody ProductDTO req,@PathVariable Long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<ProductDTO> entity = new HttpEntity<>(req,headers);
        ProductDTO updatedProduct = restTemplate.exchange(
            PRO_URL+"/"+productId+"/update", HttpMethod.PUT, entity, ProductDTO.class).getBody();
        
        return new ResponseEntity<ProductDTO>(updatedProduct,HttpStatus.OK);
	}
	
	// @PostMapping("/creates")
	// public ResponseEntity<ApiResponse> createMultipleProduct(@RequestHeader("Authorization") String jwt, @RequestBody CreateProductRequest[] reqs){
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.set("Authorization", jwt);
	// 	for(CreateProductRequest product:reqs) {
	// 		// productService.createProduct(product);
    //         HttpEntity<CreateProductRequest> entity = new HttpEntity<>(product, headers);
    //         restTemplate.exchange(
    //             PRO_URL+"/creates", HttpMethod.POST, entity, String.class).getBody();
	// 	}
	// 	ApiResponse res=new ApiResponse("products created successfully",true);
	// 	return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	// }

	@PostMapping("/creates")
public ResponseEntity<ApiResponse> createMultipleProduct(@RequestHeader("Authorization") String jwt, @RequestBody CreateProductRequest[] reqs){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<CreateProductRequest[]> entity = new HttpEntity<>(reqs, headers);
restTemplate.exchange(PRO_URL+"/creates", HttpMethod.POST, entity, String.class).getBody();
ApiResponse res=new ApiResponse("products created successfully",true);
return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
}
	
}
