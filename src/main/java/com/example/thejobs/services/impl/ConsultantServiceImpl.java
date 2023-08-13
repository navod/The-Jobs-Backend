package com.example.thejobs.services.impl;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.ConsultantDTO;
import com.example.thejobs.dto.auth.RegisterRequest;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.repo.ConsultantRepository;
import com.example.thejobs.services.AuthenticationService;
import com.example.thejobs.services.ConsultantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultantServiceImpl implements ConsultantService {

    private final ConsultantRepository consultantRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;

    @Override
    @Transactional
    public ResponsePayload registerConsultant(ConsultantDTO consultantDTO) {
        log.info("consultant register impl-method called {} name", consultantDTO.getFirstName());
        try {
            Consultant consultant = modelMapper.map(consultantDTO, Consultant.class);
            consultant.setId(UUID.randomUUID().toString());

            RegisterRequest register = modelMapper.map(consultantDTO, RegisterRequest.class);
            ResponsePayload regRes = authenticationService.register(register);

            if (regRes.getStatus() == HttpStatus.OK) {
                log.info("saved consultant {} in user table", consultantDTO.getFirstName());
                consultantRepository.save(consultant);
            } else {
                return new ResponsePayload(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), regRes.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

            }
            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "consultant added", HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Error in register consultant method");
            return new ResponsePayload(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
