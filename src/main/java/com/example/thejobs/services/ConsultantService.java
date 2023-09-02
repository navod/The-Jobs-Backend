package com.example.thejobs.services;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.consultant.ConsultantDTO;

public interface ConsultantService {

    ResponsePayload registerConsultant(ConsultantDTO consultantDTO);


    ResponsePayload updateConsultant(ConsultantDTO consultantDTO);

    ResponsePayload getAllConsultants();

    ResponsePayload deactivateConsultant(String id, boolean status);

    ResponsePayload deleteConsultant(String id);

    ResponsePayload getAvailabilityByDate(String id, String date);

    ResponsePayload getMyBooking(String id);

    ResponsePayload getConsultantDetails(String id);
}
