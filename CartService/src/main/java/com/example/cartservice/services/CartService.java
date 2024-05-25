package com.example.cartservice.services;

import com.example.cartservice.models.Cart;
import com.example.cartservice.models.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CartService {
    Page<Cart> findAllCart(int pageNo, int pageSize);
    ResponseEntity<Map<String,Object>> getUserByUsername(String username);
    Map<String,Object> convertObjectToMap(Cart cart);

     Cart getCartByUsername(String username);
}
