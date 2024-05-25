package com.example.productservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "Top5Products")
public class Top5Products {
    public static  final String ID_TOP_5_PRODUCTS = "ID_TOP_5_PRODUCTS";
    @Indexed
    private String id;
    private List<Product> products;



}
