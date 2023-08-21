package com.example.thejobs.controllers;

import com.example.thejobs.AbstractTest;
import com.example.thejobs.dto.auth.AuthenticationResponse;
import com.example.thejobs.dto.auth.RegisterRequest;
import com.example.thejobs.dto.enums.Role;
import com.example.thejobs.dto.enums.TokenType;
import com.example.thejobs.entity.Token;
import com.example.thejobs.entity.User;
import com.example.thejobs.repo.TokenRepository;
import com.example.thejobs.repo.UserRepository;
import com.example.thejobs.services.AuthenticationService;
import com.example.thejobs.services.JWTService;
import com.example.thejobs.services.impl.AuthenticationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.http.MatcherType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationControllerTest extends AbstractTest {

    @Mock
    AuthenticationService authenticationService;

    @Mock
    TokenRepository tokenRepository;

    @MockBean
    UserRepository userRepository;
    @Mock
    JWTService jwtService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void testRegister() throws Exception {
        User user = User.builder()
                .email("navod@gmail.com")
                .password("42434343434")
                .role(Role.valueOf("USER"))
                .build();

        var token = Token.builder()
                .user(user)
                .token("134343434343")
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        when(userRepository.save(user)).thenReturn(any());
        when(jwtService.generateToken(user)).thenReturn("134343434343");
        when(jwtService.generateRefreshToken(user)).thenReturn("134343434343");
        when(tokenRepository.save(token)).thenReturn(any());

        RegisterRequest request = new RegisterRequest("12", user.getEmail(), user.getPassword(),
                user.getRole());

        String inputJson = super.mapToJson(request);

        String url = "/api/v1/auth/register";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity responseEntity = mapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseEntity.class);


        assertEquals("OK", mvcResult.getResponse().getStatus());
    }


}
