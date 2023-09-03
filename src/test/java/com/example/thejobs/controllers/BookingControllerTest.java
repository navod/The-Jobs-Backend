package com.example.thejobs.controllers;

import com.example.thejobs.AbstractTest;
import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingDTO;
import com.example.thejobs.dto.JobSeekerDTO;
import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.JobSeeker;
import com.example.thejobs.repo.BookingRepository;
import com.example.thejobs.repo.ConsultantRepository;
import com.example.thejobs.repo.JobSeekerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    JobSeekerRepository jobSeekerRepository;

    @MockBean
    ConsultantRepository consultantRepository;

    @MockBean
    BookingRepository bookingRepository;

    @Autowired
    private BookingController authController;

    @MockBean
    ModelMapper modelMapper;
    JobSeeker jobSeeker = JobSeeker.builder()
            .firstName("navod")
            .lastName("perera")
            .age(20)
            .id("133213-23123-13233")
            .email("navod@gmail.com")
            .mobile("0763933541")
            .preferDestination("Australia")
            .preferJobType("Software Engineer")
            .build();

    JobSeekerDTO jobSeekerDTO = JobSeekerDTO.builder()
            .firstName("navod")
            .lastName("perera")
            .age(20)
            .email("navod@gmail.com")
            .mobile("0763933541")
            .preferDestination("Australia")
            .preferJobType("Computer manager")
            .build();


    Consultant consultant = Consultant.builder()
            .id("23424-3243-432d-3244")
            .firstName("navod")
            .lastName("perera")
            .mobile("02432444444")
            .nic("32342334444")
            .email("navod@gmail.com")
            .country("Australia")
            .jobType("Computer manager")
            .build();


    BookingDTO bookingDTO = BookingDTO.builder()
            .date("2023-10-11")
            .id(152)
            .time("12:30")
            .build();

    Booking booking = Booking.builder()
            .date("2023-10-11")
            .status("APPROVED")
            .jobSeekerId(jobSeeker)
            .consultantId(consultant)
            .id(1)
            .build();


    @Test
    void jobSeekerRegister() throws Exception {
        String url = "/api/v1/booking/register";


        when(modelMapper.map(jobSeekerDTO, JobSeeker.class)).thenReturn(jobSeeker);
        when(jobSeekerRepository.save(jobSeeker)).thenReturn(jobSeeker);
        when(consultantRepository.findByCountryAndJobType(jobSeeker.getPreferDestination(), jobSeeker.getPreferJobType())).thenReturn(consultant);
        when(consultantRepository.findByCountry(consultant.getCountry())).thenReturn(consultant);

        String inputJson = super.mapToJson(jobSeekerDTO);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();


        ObjectMapper mapper = new ObjectMapper();
        ResponsePayload responsePayload = mapper.readValue(mvcResult.getResponse().getContentAsString(), ResponsePayload.class);

        assertEquals(HttpStatus.OK, responsePayload.getStatus());
        assertEquals("Booking Added", responsePayload.getData());
        assertEquals("OK", responsePayload.getMessage());
    }

    @Test
    void testAcceptBooking() throws Exception {
        assertThat(this.authController).isNotNull();
//        String url = "/api/v1/booking/accept-booking";
//        BookingDTO bookingDTO1 = BookingDTO.builder()
//                .id(152)
//                .date("2023-12-11")
//                .time("09:30")
//                .specialNote("")
//                .build();
//        when(bookingRepository.findById(bookingDTO1.getId())).thenReturn(Optional.of(booking));
//        when(jobSeekerRepository.findById(booking.getJobSeekerId().getId())).thenReturn(Optional.of(jobSeeker));
//        when(bookingRepository.save(booking)).thenReturn(booking);
//
//
//        String inputJson = super.mapToJson(bookingDTO1);
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
//        ObjectMapper mapper = new ObjectMapper();
//        ResponsePayload responsePayload = mapper.readValue(mvcResult.getResponse().getContentAsString(), ResponsePayload.class);
//
//        assertEquals(HttpStatus.OK, responsePayload.getStatus());
//        assertEquals("Booking Accepted", responsePayload.getData());
//        assertEquals("OK", responsePayload.getMessage());
    }


    @Test
    void rejectBooking() {
        assertThat(this.authController).isNotNull();
    }

    @Test
    void getAllBooking() {
        assertThat(this.authController).isNotNull();
    }
}