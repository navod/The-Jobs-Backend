package com.example.thejobs.services;

import com.example.thejobs.dto.ConsultantDTO;
import org.springframework.http.ResponseEntity;

public interface ConsultantService {

    String registerConsulant(ConsultantDTO consultantDTO);
}
