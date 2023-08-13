package com.example.thejobs.services;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.ConsultantDTO;

public interface ConsultantService {

    ResponsePayload registerConsultant(ConsultantDTO consultantDTO);
}
