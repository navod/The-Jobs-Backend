package com.example.thejobs.services.impl;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingResponseDTO;
import com.example.thejobs.dto.dashboard.ConsultantDashboardRespDTO;
import com.example.thejobs.dto.dashboard.MainDashboardRespDTO;
import com.example.thejobs.dto.dashboard.TodayCountDTO;
import com.example.thejobs.dto.dashboard.TotalConsultantAnalytics;
import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.repo.BookingRepository;
import com.example.thejobs.repo.ConsultantRepository;
import com.example.thejobs.services.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final ConsultantRepository consultantRepository;

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
        int sheduledBookings = bookingRepository.getSheduledBookings(consultantId);

        Consultant consultant = Consultant.builder().id(consultantId).build();

        List<Booking> todayBookingDetails = bookingRepository.findBookingByConsultantIdAndStatusAndDate(consultant, "APPROVED", LocalDate.now().toString());

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
                .sheduledCount(sheduledBookings)
                .build();


        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), dto, HttpStatus.OK);

    }

    @Override
    public ResponsePayload getMainAnalytics() throws JsonProcessingException {
        Object todayBookings = bookingRepository.getAllTodayBookings();
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

        int totalPendingBookings = bookingRepository.getTotalPendingBookings();
        int totalCompletedBookings = bookingRepository.getTotalCompletedBookings();
        int totalRejectBookings = bookingRepository.getTotalRejectBookings();
        int totalDeactiveConsultants = consultantRepository.getTotalDeactiveConsultants();
        int totalActiveConsultants = consultantRepository.getTotalActiveConsultants();
        int totalConsultants = consultantRepository.getTotalConsultants();

        List<Object> objects = consultantRepository.totalConsultsAnalytics();
        List<TotalConsultantAnalytics> totalConsultantAnalytics = new ArrayList<>();

        for (Object object : objects) {
            String todayBookingsJson1 = objectMapper.writeValueAsString(object);
            JsonNode jsonNode1 = objectMapper.readTree(todayBookingsJson1);


            TotalConsultantAnalytics totalConsultantAnalytics1 = TotalConsultantAnalytics.builder()
                    .firstName(jsonNode1.get(0).asText())
                    .lastName(jsonNode1.get(2).asText())
                    .email(jsonNode1.get(3).asText())
                    .id(jsonNode1.get(1).asText())
                    .jobType(jsonNode1.get(4).asText())
                    .country(jsonNode1.get(5).asText())
                    .country(jsonNode1.get(5).asText())
                    .todayCount(jsonNode1.get(6).asInt())
                    .rejectCount(jsonNode1.get(7).asInt())
                    .completedCount(jsonNode1.get(8).asInt())
                    .pendingCount(jsonNode1.get(9).asInt())
                    .build();

            totalConsultantAnalytics.add(totalConsultantAnalytics1);
        }
        MainDashboardRespDTO dto = MainDashboardRespDTO.builder()
                .todayCountDTO(todayCountDTO)
                .pendingCount(totalPendingBookings)
                .rejectCount(totalRejectBookings)
                .totalConsultants(totalConsultants)
                .deactiveConsultants(totalDeactiveConsultants)
                .activeConsultants(totalActiveConsultants)
                .completedCount(totalCompletedBookings)
                .totalConsultantsAnalytics(totalConsultantAnalytics)
                .build();

        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), dto, HttpStatus.OK);
    }
}
