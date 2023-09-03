package com.example.thejobs.services.impl;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.MainUserDTO;
import com.example.thejobs.dto.auth.RegisterRequest;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.MainUser;
import com.example.thejobs.repo.MainUserRepository;
import com.example.thejobs.services.AuthenticationService;
import com.example.thejobs.services.MainUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainUserServiceImpl implements MainUserService {
    private final MainUserRepository mainUserRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;


    @Override
    public ResponsePayload registerMainUser(MainUserDTO mainUserDTO) {
        try {
            MainUser mainUser = modelMapper.map(mainUserDTO, MainUser.class);
            mainUser.setCreatedDate(Calendar.getInstance().getTime());
            String userId = UUID.randomUUID().toString();
            mainUser.setId(userId);
            mainUser.setStatus(true);
            RegisterRequest register = modelMapper.map(mainUserDTO, RegisterRequest.class);
            register.setId(userId);
            ResponsePayload regRes = authenticationService.register(register);

            if (regRes.getStatus() == HttpStatus.OK) {
                log.info("saved main user {} in user table", mainUserDTO.getFirstName());
                mainUserRepository.save(mainUser);

            } else {
                log.info(regRes.getMessage());
                return new ResponsePayload(regRes.getStatus().getReasonPhrase(), regRes.getMessage(), regRes.getStatus());
            }

            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "main user", HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Error in register main user method");
            return new ResponsePayload(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponsePayload getUserDetails(String id) {
        log.info("main user details method called id {}", id);

        MainUser mainUser = mainUserRepository.findById(id).get();
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), mainUser, HttpStatus.BAD_REQUEST);

    }
}
