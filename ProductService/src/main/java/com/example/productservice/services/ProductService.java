package com.example.productservice.services;

import com.example.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> findAllProduct();
    Product updateProduct(Product product);
    List<Product> getTop5Products();

}
