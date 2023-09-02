package com.example.thejobs.services.impl;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingResponseDTO;
import com.example.thejobs.dto.dashboard.ConsultantDashboardRespDTO;
import com.example.thejobs.dto.dashboard.TodayCountDTO;
import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.repo.BookingRepository;
import com.example.thejobs.services.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Override
    public ResponsePayload getDashboardAnalytics(String consultantId) throws JsonProcessingException {


        Object todayBookings = bookingRepository.getTodayBookings(consultantId);
        String todayBookingsJson = objectMapper.writeValueAsString(todayBookings);

        // Parse the JSON string into a JSON object (Jackson)
        JsonNode jsonNode = objectMapper.readTree(todayBookingsJson);
        TodayCountDTO todayCountDTO = TodayCountDTO.builder()
                .yesterday(jsonNode.get(0).asText())
                .today(jsonNode.get(1).asText())
                .yesterdayCount(jsonNode.get(2).asInt())
                .todayCount(jsonNode.get(3).asInt())
                .increaseToday(jsonNode.get(4).asInt())
                .build();

        int totalPendingBookings = bookingRepository.getTotalPendingBookings(consultantId);
        int totalCompletedBookings = bookingRepository.getTotalCompletedBookings(consultantId);
        int totalRejectBookings = bookingRepository.getTotalRejectBookings(consultantId);

        Consultant consultant = Consultant.builder().id(consultantId).build();
        List<Booking> todayBookingDetails = bookingRepository.findBookingByConsultantIdAndStatusAndDate(consultant,"APPROVED","2023-09-11");

        List<BookingResponseDTO> bookingDTO = new ArrayList<>();

        for (Booking booking : todayBookingDetails) {
            bookingDTO.add(modelMapper.map(booking, BookingResponseDTO.class));
        }


        ConsultantDashboardRespDTO dto = ConsultantDashboardRespDTO.builder()
                .todayCountDTO(todayCountDTO)
                .pendingCount(totalPendingBookings)
                .rejectCount(totalRejectBookings)
                .todayBookingDetails(bookingDTO)
                .completedCount(totalCompletedBookings)
                .build();


        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), dto, HttpStatus.OK);

    }
}
