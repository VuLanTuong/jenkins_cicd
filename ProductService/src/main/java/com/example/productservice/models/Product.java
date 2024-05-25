package com.example.productservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @Indexed
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long id;
    @Column(name = "product_name")
    private String name;
    private double price;
    private String description;
    private int quantity;
    private String unit;
    private  boolean isDeleted;
    public Product(String name, double price, String description, int quantity, String unit, Boolean isDeleted) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
        this.isDeleted = isDeleted;
    }
}
