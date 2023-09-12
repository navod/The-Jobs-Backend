package com.example.thejobs.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingDTO;
import com.example.thejobs.dto.BookingResponseDTO;
import com.example.thejobs.dto.JobSeekerDTO;
import com.example.thejobs.dto.consultant.ConsultantDTO;
import com.example.thejobs.dto.dashboard.ConsultantDashboardRespDTO;
import com.example.thejobs.dto.dashboard.MainDashboardRespDTO;
import com.example.thejobs.dto.dashboard.TodayCountDTO;
import com.example.thejobs.dto.enums.Role;
import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.JobSeeker;
import com.example.thejobs.repo.BookingRepository;
import com.example.thejobs.repo.ConsultantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;

import java.math.BigInteger;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DashboardServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DashboardServiceImplTest {
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ConsultantRepository consultantRepository;

    @Autowired
    private DashboardServiceImpl dashboardServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    @Disabled("TODO: Complete this test")
    void testGetDashboardAnalytics() throws JsonProcessingException {
        when(bookingRepository.getTodayBookings(Mockito.<String>any())).thenReturn("Today Bookings");
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
        when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
        dashboardServiceImpl.getDashboardAnalytics("42");
    }


    @Test
    @Disabled("TODO: Complete this test")
    void testGetMainAnalytics() throws JsonProcessingException {

        when(bookingRepository.getAllTodayBookings()).thenReturn("All Today Bookings");
        when(objectMapper.readTree(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
        when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
        dashboardServiceImpl.getMainAnalytics();
    }

}

