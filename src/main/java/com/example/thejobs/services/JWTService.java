package com.example.thejobs.services;

import com.example.thejobs.dto.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
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
