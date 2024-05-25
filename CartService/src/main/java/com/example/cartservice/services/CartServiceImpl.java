package com.example.cartservice.services;

import com.example.cartservice.models.Cart;
import com.example.cartservice.models.CartItem;
import com.example.cartservice.repositories.CartItemRepository;
import com.example.cartservice.repositories.CartRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    CartRepository repository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Override
    public Page<Cart> findAllCart(int pageNo, int pageSize) {
        int startItem = pageNo*pageSize;
        List<Cart> list;
        List<Cart> carts = repository.findAll();
        if(carts.size()< startItem){
            list = Collections.emptyList();
        }else{
            int toIndex = Math.min(startItem+ pageSize,carts.size());
            list = carts.subList(startItem,toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(pageNo,pageSize),carts.size());
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUserByUsername(String username) {
        return null;
    }


    public ResponseEntity<String> rateLimiterFallback(long id, RequestNotPermitted e) {
        // Return a suitable response when the circuit breaker is open
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Request is not permitted");
    }
    @Override
    public Map<String, Object> convertObjectToMap(Cart cart) {
        Map<String, Object> map = new HashMap<>();
        // Lấy danh sách các trường của đối tượng
        Field[] fields = cart.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                // Cho phép truy cập vào các trường private
                field.setAccessible(true);
                // Đưa tên trường và giá trị của trường vào map
                map.put(field.getName(), field.get(cart));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public Cart getCartByUsername(String username) {
        return  repository.findCartByUsername(username);
    }

}
