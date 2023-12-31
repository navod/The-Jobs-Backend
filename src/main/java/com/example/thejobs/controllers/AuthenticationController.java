package com.example.thejobs.controllers;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.auth.AuthenticationRequest;
import com.example.thejobs.dto.auth.AuthenticationResponse;
import com.example.thejobs.dto.auth.RegisterRequest;
import com.example.thejobs.services.AuthenticationService;
import com.example.thejobs.services.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<ResponsePayload> register(
            @RequestBody RegisterRequest request
    ) {
        log.info("user register details : RegisterRequest | {} ", request);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<ResponsePayload> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        log.info("login user");
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        log.info("refresh token method called");
        authenticationService.refreshToken(request, response);
    }

}
