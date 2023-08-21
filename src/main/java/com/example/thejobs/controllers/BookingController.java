package com.example.thejobs.controllers;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingDTO;
import com.example.thejobs.dto.JobSeekerDTO;
import com.example.thejobs.dto.consultant.ConsultantDTO;
import com.example.thejobs.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
@Slf4j
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/register")
    public ResponsePayload registerConsultant(@RequestBody @Valid JobSeekerDTO jobSeekerDTO) {
        log.info("consultant register method called {} name", jobSeekerDTO.getFirstName());
        return bookingService.saveBooking(jobSeekerDTO);
    }

    @PostMapping("/accept-booking")
    public ResponsePayload acceptBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        log.info("consultant register method called {} name", bookingDTO.getId());
        return bookingService.acceptBooking(bookingDTO);
    }
}
