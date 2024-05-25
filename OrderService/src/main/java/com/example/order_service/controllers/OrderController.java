package com.example.order_service.controllers;

import com.example.order_service.models.Order;
import com.example.order_service.repositories.OrderRepository;
import com.example.order_service.services.OrderService;
import com.example.order_service.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/OrderService")
public class OrderController {
    @Autowired
    OrderRepository repository;
    @Autowired
    OrderService orderService;
    @Autowired
    TokenService tokenService;

    @GetMapping("findAll")
    public ResponseEntity<?> getAllOrder(@RequestHeader("token") String token) {
        boolean isAdmin = tokenService.isAdmin(token);
        if(isAdmin){
            List<Order> orders = orderService.findAllOrder();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @PostMapping("create")
    public ResponseEntity<?> saveOrder(@RequestBody Order order,@RequestHeader("token") String token) {
        try {
            if (order != null) {
                if(token == null){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Header token is null!");
                }
                String username = tokenService.getUsernameFromToken(token);
                System.out.println(token);
                if (username == null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                order.setUsername(tokenService.getUsernameFromToken(token));
                Order saveOrder = repository.save(order);
                return ResponseEntity.status(HttpStatus.OK).body(saveOrder);
            }
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("orders")
    public ResponseEntity<?> getOrderByUser(@RequestHeader("token") String token) {
        String username = tokenService.getUsernameFromToken(token);
        List<Order> orderPage = orderService.findOrderByUsername(username);
        return new ResponseEntity<>(orderPage, HttpStatus.OK);
    }
//    @GetMapping("order/{id}")
//    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
//        try {
//            Order order = repository.findById(id).orElse(null);
//            if (order == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
//            }
//            if(orderService.getUserByUsername(order.getUsername()).getStatusCode()== HttpStatus.OK){
//                Map<String, Object> mapOrder = orderService.convertObjectToMap(order);
//                mapOrder.remove("userId");
//                mapOrder.put("user", orderService.getUserByUsername(order.getUsername()).getBody());
//                return ResponseEntity.status(HttpStatus.OK).body(mapOrder);
//            }else {
//                return orderService.getUserByUsername(order.getUsername());
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
//        }
//    }
}
