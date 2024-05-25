package com.example.authservice.config;


import com.example.authservice.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter = new JwtRequestFilter();


    //require disable csrf
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/AuthService/welcome", "/AuthService/login/**", "/AuthService/findByToken/**", "/AuthService/register", "/AuthService/login", "/AuthService/registerAdmin",
                                        "/AuthService/updateUser", "/AuthService/deleteUser", "/AuthService/info").permitAll()
                                .anyRequest().authenticated()
                ).csrf(AbstractHttpConfigurer::disable).
                httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
