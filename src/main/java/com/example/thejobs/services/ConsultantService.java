package com.example.thejobs.services;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.consultant.ConsultantDTO;

public interface ConsultantService {

    ResponsePayload registerConsultant(ConsultantDTO consultantDTO);


}