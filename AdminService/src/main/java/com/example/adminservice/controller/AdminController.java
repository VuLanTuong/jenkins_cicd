package com.example.adminservice.controller;

import com.example.adminservice.model.Admin;
import com.example.adminservice.service.AdminService;
import com.example.adminservice.service.AdminServiceImpl;
import com.example.adminservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AdminService")
public class AdminController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminServiceImpl adminService;
    @GetMapping("/")
    public String sayHello(){
        return "hello";
    }

    @GetMapping("/info")
    public String getInfo(@RequestHeader("token") String token){
        System.out.println("Token " + token);
        tokenService.readToken(token);
        boolean result = tokenService.isAdmin(token);
        if(result){
            return "admin";
        }
        return "information";
    }


    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmin(@RequestHeader("token") String token){
        System.out.println("Token " + token);
        tokenService.readToken(token);
        boolean result = tokenService.isAdmin(token);
        if(result){
            List<Admin> admins = adminService.getAllAdmins();
            return new ResponseEntity<>(admins, HttpStatus.OK);
        }
        return new ResponseEntity<>("You not have permissions", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<?> addCustomer(@RequestBody Admin user){
        Admin admin = new Admin();
        admin.setUsername(user.getUsername());
//        boolean result = tokenService.isAdmin(token);
//        if(result){

        adminService.addAdmin(admin);
        return new ResponseEntity<String>("add admin successful", HttpStatus.OK);
//        }
//        return new ResponseEntity<String>("you not have permission", HttpStatus.BAD_REQUEST);

    }
}
