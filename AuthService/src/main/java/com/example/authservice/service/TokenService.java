package com.example.authservice.service;


import com.example.authservice.entity.Token;

public interface TokenService {
    Token createToken(Token token);

    Token findByToken(String token);

    boolean isAdmin(String token);
}
