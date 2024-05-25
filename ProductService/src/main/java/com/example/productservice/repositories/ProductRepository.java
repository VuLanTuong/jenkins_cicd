package com.example.productservice.repositories;

import com.example.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p where p.isDeleted = false")
    List<Product> findAllProduct();

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.quantity > 100 ORDER BY p.quantity desc LIMIT 5")
    List<Product> getTop5Products();
}
