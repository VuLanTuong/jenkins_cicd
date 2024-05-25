package com.example.gatewayjwt.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

        public static final List<String> openApiEndpoints = List.of(
                        "/AuthService/register",
                        "/AuthService/login",
                "/AuthService/registerAdmin",
                "/AuthService/welcome",
                "/CustomerService/hello",
                "/AdminService/addAdmin"


        );

        public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
                        .stream()
                        .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
