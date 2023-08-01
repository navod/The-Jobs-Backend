package com.example.thejobs.controllers;

import com.example.thejobs.dto.ConsultantDTO;
import com.example.thejobs.services.ConsultantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consultant")
public class ConsultantController {

    private final ConsultantService consultantService;


    @PostMapping("/register")
    public String registerConsultant(@RequestBody @Valid ConsultantDTO consultantDTO) {
        return consultantService.registerConsulant(consultantDTO);
    }
}
