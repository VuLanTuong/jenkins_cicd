package com.example.authservice.service;


import com.example.authservice.authen.UserPrincipal;
import com.example.authservice.entity.User;

public interface UserService {
    void createUser(User user);
    UserPrincipal findByUsername(String username);


    void createAdmin(User user);

    boolean isExistUsername(String username);

    User updateUser(User user);
    User deleteUser(User user);
}
