package com.example.productservice.controllers;

import com.example.productservice.models.Product;
import com.example.productservice.models.Top5Products;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.repositories.Top5ProductRepository;
import com.example.productservice.services.ProductService;
import com.example.productservice.services.TokenService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.productservice.models.Top5Products.ID_TOP_5_PRODUCTS;

@RestController
@RequestMapping("/ProductService")
public class ProductController {
    @Autowired
    ProductRepository repository;
    @Autowired
    ProductService productService;

    @Autowired
    TokenService tokenService;

    @Autowired
    Top5ProductRepository top5ProductRepository;
    @GetMapping("products")
    public ResponseEntity<?> getAllProduct() {
            return new ResponseEntity<>(productService.findAllProduct(), HttpStatus.OK);

    }

    @GetMapping("/top5Products")
    public ResponseEntity<?> getTop5Products(){
        Optional<Top5Products> products = top5ProductRepository.findById(ID_TOP_5_PRODUCTS);
        if(products.isEmpty()){
            Top5Products top5Products = new Top5Products();
            List<Product> products1 = productService.getTop5Products();
            top5Products.setId(ID_TOP_5_PRODUCTS);
            top5Products.setProducts(products1);
            top5ProductRepository.save(top5Products);
            return new ResponseEntity<>(top5Products, HttpStatus.OK);

        }
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id, @RequestHeader("token") String token ) {
        try {
//            boolean isAdmin = tokenService.isAdmin(token);
//            if(!isAdmin) {
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//            }
            Product product = repository.findById(id).orElse(null);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }

            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create")
    public ResponseEntity<?> createProduct(@RequestBody Product product, @RequestHeader("token") String token ) {
        try {
            boolean isAdmin = tokenService.isAdmin(token);
            if (!isAdmin) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (product != null) {
                Product saveProduct = repository.save(product);
                return ResponseEntity.status(HttpStatus.OK).body(saveProduct);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new
                    ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@RequestHeader("token") String token, @RequestBody Product product) {
        try {
            boolean isAdmin = tokenService.isAdmin(token);
            if (!isAdmin) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Product product1 = repository.findById(product.getId()).orElse(null);
            if (product1 == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            }
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            product1.setQuantity(product.getQuantity());
            product1.setDeleted(product.isDeleted());
            product1.setUnit(product.getUnit());
            product1.setDescription(product.getDescription());
           repository.save(product1);
            return ResponseEntity.status(HttpStatus.OK).body("Update product successfully!");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id, @RequestHeader("token") String token){
        try {
            boolean isAdmin = tokenService.isAdmin(token);
            if(!isAdmin) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Product product = repository.findById(id).orElse(null);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            }
            product.setDeleted(true);
            repository.save(product);
            return ResponseEntity.status(HttpStatus.OK).body("Delete product successfully!");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
