package com.example.customerservice.controller;


import com.example.customerservice.model.Customer;
import com.example.customerservice.service.CustomerService;
import com.example.customerservice.service.CustomerServiceImpl;
import com.example.customerservice.service.TokenService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/CustomerService")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private TokenService tokenService;

    private final String gatewayBaseUrl;


    public CustomerController(CustomerServiceImpl customerService, 
    TokenService tokenService,
    @Value("${gateway.uri}") String gatewayUri,
    @Value("${gateway.port}") int gatewayPort) {
        this.customerService = customerService;
        this.tokenService = tokenService;
        this.gatewayBaseUrl = String.format("http://%s:%d", gatewayUri, gatewayPort);
}


    @GetMapping("/hello")
    @CircuitBreaker(name = "AuthService", fallbackMethod = "fallbackHello" )
    public String hello() {
        RestTemplate restTemplate = new RestTemplate();
//                return restTemplate.getForObject("http://localhost:8888/AuthService/welcome", String.class);
        String apiUrl = this.gatewayBaseUrl + "/AuthService/welcome";
        System.out.println(apiUrl);
        return restTemplate.getForObject( apiUrl, String.class);
    }

    public String fallbackHello(Exception e) {
        return "auth service does not permit further calls";
    }
    
    public String fallbackRetry(Exception e){
        return "all retries have exhausted";
    }



    @GetMapping("/customers")
    @Retry(name = "retry", fallbackMethod = "fallbackRetry")
    public List<Customer> getAll(@RequestHeader("token") String token){
        boolean result = tokenService.isAdmin(token);
        if(result){
            return customerService.getAllCustomer();

        }
        return null;

    }

    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer user, @RequestHeader("token") String token){
//        boolean result = tokenService.isAdmin(token);
//        if(result){
//            System.out.println("register");
//            user.setActive(true);
            customerService.addCustomer(user);
            return new ResponseEntity<>("add customer successful", HttpStatus.OK);
//        }
//        return new ResponseEntity<String>("you not have permission", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer user, @RequestHeader("token") String token){
//        boolean result = tokenService.isAdmin(token);
//        if(result){
//            System.out.println("register");
//            user.setActive(true);
            customerService.updateCustomer(user);
            return new ResponseEntity<>("update customer successful", HttpStatus.OK);
//        }
//        return new ResponseEntity<String>("you not have permission", HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/deleteCustomer")
    public ResponseEntity<?> deleteCustomer(@RequestBody Customer user, @RequestHeader("token") String token){
        boolean result = tokenService.isAdmin(token);
        if(result){
            customerService.deleteCustomer(user);
            return new ResponseEntity<String>("delete customer successful", HttpStatus.OK);
        }
        return new ResponseEntity<String>("you not have permission", HttpStatus.BAD_REQUEST);

    }



}
