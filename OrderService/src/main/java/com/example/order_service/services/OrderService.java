package com.example.order_service.services;

import com.example.order_service.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Order> findAllOrder();
    List<Order> findOrderByUsername(String username);
    ResponseEntity<Map<String,Object>> getUserByUsername(String username);

}
