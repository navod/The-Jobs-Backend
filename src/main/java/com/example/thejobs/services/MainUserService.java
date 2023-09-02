package com.example.thejobs.services;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.MainUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface MainUserService {
    ResponsePayload registerMainUser(MainUserDTO mainUserDTO);

    ResponsePayload getUserDetails(String id);
}
