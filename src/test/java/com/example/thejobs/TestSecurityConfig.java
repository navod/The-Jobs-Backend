package com.example.thejobs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig {
    private final String[] list = {
            "/api/v1/auth/**",
            "/api/v1/booking/**",
    };
    protected void configure(HttpSecurity http) throws Exception {
        // Override the security configuration for testing purposes
        http.authorizeHttpRequests()
                .requestMatchers(list).permitAll() // Allow access to the test endpoint
                .anyRequest().authenticated(); // Require authentication for other endpoints
        // Other security configurations...
    }
}

