package com.example.thejobs.controllers;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.consultant.ConsultantDTO;
import com.example.thejobs.services.ConsultantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consultant")
@Slf4j
public class ConsultantController {

    private final ConsultantService consultantService;


    @PostMapping("/register")
    public ResponsePayload registerConsultant(@RequestBody @Valid ConsultantDTO consultantDTO) {
        log.info("consultant register method called {} name", consultantDTO.getFirstName());
        return consultantService.registerConsultant(consultantDTO);
    }

    @PutMapping("/update-consultant")
    public ResponsePayload updateConsultant(@RequestBody @Valid ConsultantDTO consultantDTO) {
        log.info("consultant update method called {} name", consultantDTO.getFirstName());
        return consultantService.updateConsultant(consultantDTO);
    }

    @GetMapping("/get-all")
    public ResponsePayload getAllConsultants() {
        log.info("consultant get method called");
        return consultantService.getAllConsultants();
    }

    @PutMapping("/activation")
    public ResponsePayload deactivateConsultant(@RequestParam String id,boolean status) {
        log.info("consultant activation method called");
        return consultantService.deactivateConsultant(id,status);
    }

    @DeleteMapping("/delete-consultant")
    public ResponsePayload deleteConsultant(@RequestParam String id) {
        log.info("consultant delete method called");
        return consultantService.deleteConsultant(id);
    }
}
