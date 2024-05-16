package com.sudipta.product;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;


import com.sudipta.product.modal.Category;
import com.sudipta.product.modal.Product;
import com.sudipta.product.repository.CategoryRepository;
import com.sudipta.product.repository.ProductRepository;
import com.sudipta.product.request.CreateProductRequest;
import com.sudipta.product.serviceImpl.ProductServiceImplementation;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplementationTest {

    @InjectMocks
    private ProductServiceImplementation productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateProduct() {
        // Mock data
        CreateProductRequest req = new CreateProductRequest();
        req.setTitle("Test Product");
        req.setColor("Red");
        req.setDescription("Test Description");
        req.setDiscountedPrice(50);
        req.setDiscountPersent(10);
        req.setImageUrl("test.jpg");
        req.setBrand("Test Brand");
        req.setPrice(100);
        //req.setSize("M");
        req.setQuantity(5);
        req.setTopLavelCategory("Top Category");
        req.setSecondLavelCategory("Second Category");
        req.setThirdLavelCategory("Third Category");

        Category mockCategory = new Category();
        mockCategory.setName("Third Category");
        mockCategory.setLevel(3);

        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setTitle("Test Product");

        when(categoryRepository.findByName(anyString())).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        // Call the method
        Product createdProduct = productService.createProduct(req);

        // Verify the result
        assertNotNull(createdProduct);
        assertEquals(mockProduct.getId(), createdProduct.getId());
        assertEquals(mockProduct.getTitle(), createdProduct.getTitle());
    }

    

    @Test
    public void testGetAllProduct() {
        // Mock data
        String category = "Test Category";
        List<String> colors = new ArrayList<>();
        List<String> sizes = new ArrayList<>();
        Integer minPrice = 0;
        Integer maxPrice = 100;
        Integer minDiscount = 0;
        String sort = "asc";
        String stock = "in_stock";
        Integer pageNumber = 0;
        Integer pageSize = 10;

        List<Product> mockProducts = new ArrayList<>();
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setTitle("Test Product");
        mockProduct.setColor("Red");
        //mockProduct.setSize("M");
        mockProduct.setPrice(50);
        mockProduct.setDiscountedPrice(45);
        mockProduct.setQuantity(10);
        mockProducts.add(mockProduct);


        when(productRepository.filterProducts(anyString(), anyInt(), anyInt(), anyInt(), anyString()))
                .thenReturn(mockProducts);

        // Call the method
        Page<Product> resultPage = productService.getAllProduct(category, colors, sizes, minPrice, maxPrice,
                minDiscount, sort, stock, pageNumber, pageSize);

        // Verify the result
        assertNotNull(resultPage);
        assertEquals(mockProducts.size(), resultPage.getContent().size());
        assertEquals(mockProduct.getId(), resultPage.getContent().get(0).getId());
    }

    
}

