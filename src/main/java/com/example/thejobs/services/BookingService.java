package com.example.thejobs.services;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingDTO;
import com.example.thejobs.dto.JobSeekerDTO;

public interface BookingService {
    ResponsePayload saveBooking(JobSeekerDTO jobSeekerDTO);

    ResponsePayload acceptBooking(BookingDTO bookingDTO);

    ResponsePayload getAllBooking();

    ResponsePayload rejectBooking(BookingDTO bookingDTO);

    ResponsePayload completeBooking(int id);

}
