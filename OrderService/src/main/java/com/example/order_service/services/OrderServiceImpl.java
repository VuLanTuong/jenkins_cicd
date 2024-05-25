package com.example.order_service.services;

import com.example.order_service.models.Order;
import com.example.order_service.repositories.OrderRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository repository;
    @Override
    public List<Order> findAllOrder() {


       return repository.findAll();

    }

    @Override
    public List<Order> findOrderByUsername(String username) {
        List<Order> list;
        return repository.findOrderByUsername(username);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUserByUsername(String username) {

                return null;
    }

}
