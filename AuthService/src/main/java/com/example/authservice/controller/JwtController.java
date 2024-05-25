package com.example.authservice.controller;


import com.example.authservice.authen.UserPrincipal;
import com.example.authservice.entity.Token;
import com.example.authservice.entity.User;
import com.example.authservice.service.TokenService;
import com.example.authservice.service.UserService;
import com.example.authservice.util.JwtUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/AuthService")
@Slf4j
public class JwtController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;


    @GetMapping("/welcome")
    @RateLimiter(name = "AuthService", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<String> welcome()
    {
        return new ResponseEntity<String>("welcome", HttpStatus.OK);
    }



    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        System.out.println("register");
        boolean result = userService.isExistUsername(user.getUsername());
        if(!result){
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.createUser(user);
            return new ResponseEntity<String>("register successful", HttpStatus.OK);

        }
        return new ResponseEntity<String>("username is exist", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody User user){
        boolean result = userService.isExistUsername(user.getUsername());
        System.out.println(result);
        if(!result){
            // RestTemplate restTemplate = new RestTemplate();
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.createAdmin(user);
            return new ResponseEntity<>("register admin success", HttpStatus.OK);

//            restTemplate.getForObject("http://localhost:8888/AdminService/addAdmin", User.class);
            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.APPLICATION_JSON);


//        SubjectDTO subject = new SubjectDTO();
//             HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

// //            String url = "http://localhost:8888/AdminService/addAdmin";
//             String url = "http://gateway:8888/AdminService/addAdmin";

//             ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

//             if (response.getStatusCode().is2xxSuccessful()) {
//                 String responseBody = response.getBody();
//                 System.out.println("Response: " + responseBody);
//                 return new ResponseEntity<String>("register admin successful", HttpStatus.OK);

//             } else {
// System.out.println("Response: 400 "); 
            }
            return new ResponseEntity<>("username is exist", HttpStatus.BAD_REQUEST);


        }

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        UserPrincipal userPrincipal =
                userService.findByUsername(user.getUsername());

        if (null == user || !new BCryptPasswordEncoder()
                .matches(user.getPassword(), userPrincipal.getPassword())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Account or password is not valid!");
        }

        System.out.println("user"+ user);
//        userPrincipal.setAuthorities(UserPrincipal.getAuthorities(user.getRoles()));

        System.out.println("user principal" + userPrincipal);
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());


        log.info( token.getToken());
        tokenService.createToken(token);

        return ResponseEntity.ok(token.getToken());
    }


//    @GetMapping("/findByToken")
//    public ResponseEntity findByToken(@RequestHeader String token){
//        Token token1 = tokenService.findByToken(token);
//        return ResponseEntity.ok();
//
//    }

    public ResponseEntity<String> rateLimiterFallback(Exception e){
        return new ResponseEntity<String>("auth service does not permit further calls", HttpStatus.TOO_MANY_REQUESTS);

    }


    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestHeader("token") String token, @RequestBody User user){
        boolean isAdmin = tokenService.isAdmin(token);
        if(isAdmin){
            User user1 = userService.updateUser(user);
            return new ResponseEntity<User>(user1, HttpStatus.OK);
        }
        return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<User> deleteUser(@RequestHeader("token") String token,@RequestBody User user){
        System.out.println( "*****Token "+token);
        boolean isAdmin = tokenService.isAdmin(token);

        if(isAdmin){
            User user1 = userService.deleteUser(user);
            return new ResponseEntity<User>(user1, HttpStatus.OK);
        }
        return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }

//    @GetMapping("/info")
//    public String getInfo(@RequestHeader("token") String token){
//        System.out.println("Token " + token);
//        boolean result = tokenService.isAdmin(token);
//        if(result){
//            return "admin";
//        }
//        return "information";
//    }

    public String fallbackHello(Exception e){
        return "admin service does not permit further calls";

    }




}




