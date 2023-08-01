package com.example.thejobs.services.impl;

import com.example.thejobs.dto.ConsultantDTO;
import com.example.thejobs.services.ConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConsultantServiceImpl implements ConsultantService {
    @Override
    public String registerConsulant(ConsultantDTO consultantDTO) {
        try {
            return "consultant added";
        } catch (Exception e) {
            return e.getMessage().toString();
        }
    }
}
