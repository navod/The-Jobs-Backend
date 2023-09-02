package com.example.thejobs.controllers;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.services.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;


    @GetMapping("/get-consultant-analytics")
    public ResponsePayload getConsultantDetails(@RequestParam String id) throws JsonProcessingException {
        log.info("get consultant analytics method called");
        return dashboardService.getDashboardAnalytics(id);
    }
}
