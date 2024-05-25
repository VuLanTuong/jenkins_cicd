package com.example.cartservice.controllers;

import com.example.cartservice.models.Cart;
import com.example.cartservice.models.CartItem;
import com.example.cartservice.repositories.CartItemRepository;
import com.example.cartservice.repositories.CartRepository;
import com.example.cartservice.services.CartService;
import com.example.cartservice.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/CartService")
public class CartController {
    @Autowired
    CartRepository repository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartService cartService;
    @Autowired
    TokenService tokenService;


    @PostMapping("create")
    public ResponseEntity<?> saveCart(@RequestBody Cart cart,@RequestHeader("token") String token) {
        try {
            if (cart != null) {
                if(token == null){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is null");
                }
                String username = tokenService.getUsernameFromToken(token);
                System.out.println(token);
                if (username == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username is null");
                }
                cart.setUsername(username);
                Cart saveCart = repository.save(cart);
                return ResponseEntity.status(HttpStatus.OK).body(saveCart);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart is null");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("cart")
    public ResponseEntity<?> getCartByUser(@RequestHeader("token") String token) {
        try {
            String username = tokenService.getUsernameFromToken(token);
            Cart cart = repository.findCartByUsername(username);
            System.out.println(cart);
            if (cart == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
            }
           else{
               return ResponseEntity.status(HttpStatus.OK).body(cart);
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something is error from server");
        }
    }
    @DeleteMapping("deleteItem/{id}")
    public ResponseEntity<?> deleteCartItem(@RequestHeader("token") String token, @PathVariable("id") Long id)  {
        System.out.println("id: " + id);
        System.out.println("token: " + token);
        try {
            CartItem cartItem = cartItemRepository.findById(id).orElse(null);
            String username = tokenService.getUsernameFromToken(token);
            Cart cart = repository.findCartByUsername(username);

            if (cartItem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CartItem not found.");
            }

            if (cart == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found.");
            }
            cart.getListCartItem().remove(cartItem);
            repository.save(cart);
            return ResponseEntity.status(HttpStatus.OK).body("Delete item successfully!");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something is error from server.");
        }
    }
    @PutMapping("/addItem")
    public ResponseEntity<?>addCartItem(@RequestBody CartItem cartItem, @RequestHeader("token") String token){
        try{
            String username = tokenService.getUsernameFromToken(token);
            Cart cart = repository.findCartByUsername(username);

            if(cart == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found Cart");
            }
            cart.getListCartItem().add(cartItem);
            Cart saveCart = repository.save(cart);
            return ResponseEntity.status(HttpStatus.OK).body(saveCart);
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something is error from server!");
        }
    }

}
