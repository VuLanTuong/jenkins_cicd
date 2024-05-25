package com.example.cartservice.repositories;

import com.example.cartservice.models.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.id = :id")
    void deleteCartItemById(@Param("id") long id);
}
