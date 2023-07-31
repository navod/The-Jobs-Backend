package com.example.thejobs.services;

import com.example.thejobs.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public interface JWTService {
    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(User userDetails);

    String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    );

    String generateRefreshToken(
            UserDetails userDetails
    );


    boolean isTokenValid(String token, UserDetails userDetails);
}
