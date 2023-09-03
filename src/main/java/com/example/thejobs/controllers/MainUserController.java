package com.example.thejobs.controllers;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.MainUserDTO;
import com.example.thejobs.services.MainUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main-user")
@Slf4j
public class MainUserController {

    private final MainUserService mainUserService;

    @PostMapping("/register")
    public ResponsePayload getConsultantDetails(@RequestBody MainUserDTO mainUserDTO) {
        log.info("main user adding method called");
        return mainUserService.registerMainUser(mainUserDTO);
    }


    @GetMapping("/get-main-user")
    public ResponsePayload getConsultantDetails(@RequestParam String id) {
        log.info("get consultant detail method called");
        return mainUserService.getUserDetails(id);
    }
}
