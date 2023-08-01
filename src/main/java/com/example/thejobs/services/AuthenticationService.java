package com.example.thejobs.services;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.auth.AuthenticationRequest;
import com.example.thejobs.dto.auth.AuthenticationResponse;
import com.example.thejobs.dto.auth.RegisterRequest;
import com.example.thejobs.entity.User;
import com.example.thejobs.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    ResponsePayload register(RegisterRequest request);

    ResponsePayload authenticate(AuthenticationRequest request);

    void saveUserToken(User user, String jwtToken);

    void refreshToken(HttpServletRequest request, HttpServletResponse response
    ) throws IOException;
}
