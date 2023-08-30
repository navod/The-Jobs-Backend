package com.example.thejobs.services;

import com.example.thejobs.advice.ResponsePayload;

import java.util.Map;

public interface NotificationService {
    ResponsePayload sendBookingConfirmEmail(String toEmail, Map<String, Object> model);

    ResponsePayload sendBookingPendingEmail(String email, Map<String, Object> model);
}
