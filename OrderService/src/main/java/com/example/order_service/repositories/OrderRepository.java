package com.example.order_service.repositories;

import com.example.order_service.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {
    public List<Order> findOrderByUsername(String username);
}
