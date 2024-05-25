package com.example.cartservice.repositories;

import com.example.cartservice.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    public Cart findCartByUsername(String username);
}
