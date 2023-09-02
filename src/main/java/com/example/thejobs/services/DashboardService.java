package com.example.thejobs.services;

import com.example.thejobs.advice.ResponsePayload;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DashboardService {

    ResponsePayload getDashboardAnalytics(String consultantId) throws JsonProcessingException;
}
