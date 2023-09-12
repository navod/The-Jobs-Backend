package com.example.thejobs.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingResponseDTO;
import com.example.thejobs.dto.consultant.ConsultantDTO;
import com.example.thejobs.dto.consultant.ConsultantRespDTO;
import com.example.thejobs.dto.consultant.TimeSlots;
import com.example.thejobs.dto.enums.DAYS;
import com.example.thejobs.dto.enums.Role;
import com.example.thejobs.entity.Availability;
import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.JobSeeker;
import com.example.thejobs.entity.User;
import com.example.thejobs.repo.AvailabilityRepository;
import com.example.thejobs.repo.BookingRepository;
import com.example.thejobs.repo.ConsultantRepository;
import com.example.thejobs.repo.UserRepository;
import com.example.thejobs.services.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ConsultantServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ConsultantServiceImplTest {
    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private AvailabilityRepository availabilityRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ConsultantRepository consultantRepository;

    @Autowired
    private ConsultantServiceImpl consultantServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testRegisterConsultant() {
        Consultant consultant = new Consultant();
        consultant.setCountry("GB");
        consultant.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant.setEmail("jane.doe@example.org");
        consultant.setFirstName("Jane");
        consultant.setId("42");
        consultant.setJobType("Job Type");
        consultant.setLastName("Doe");
        consultant.setMobile("Mobile");
        consultant.setNic("Nic");
        consultant.setRole(Role.ADMIN);
        consultant.setStatus(true);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Consultant>>any())).thenReturn(consultant);
        ResponsePayload actualRegisterConsultantResult = consultantServiceImpl.registerConsultant(new ConsultantDTO());
        assertEquals("class com.example.thejobs.entity.Consultant cannot be cast to class com.example.thejobs.dto.auth"
                     + ".RegisterRequest (com.example.thejobs.entity.Consultant and com.example.thejobs.dto.auth.RegisterRequest"
                     + " are in unnamed module of loader com.diffblue.cover.g.g @43d08fc6)",
                actualRegisterConsultantResult.getData());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualRegisterConsultantResult.getStatus());
        assertEquals("Internal Server Error", actualRegisterConsultantResult.getMessage());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
    }

    @Test
    void testUpdateConsultant() {
        Consultant consultant = new Consultant();
        consultant.setCountry("GB");
        consultant.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant.setEmail("jane.doe@example.org");
        consultant.setFirstName("Jane");
        consultant.setId("42");
        consultant.setJobType("Job Type");
        consultant.setLastName("Doe");
        consultant.setMobile("Mobile");
        consultant.setNic("Nic");
        consultant.setRole(Role.ADMIN);
        consultant.setStatus(true);
        Optional<Consultant> ofResult = Optional.of(consultant);

        Consultant consultant2 = new Consultant();
        consultant2.setCountry("GB");
        consultant2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant2.setEmail("jane.doe@example.org");
        consultant2.setFirstName("Jane");
        consultant2.setId("42");
        consultant2.setJobType("Job Type");
        consultant2.setLastName("Doe");
        consultant2.setMobile("Mobile");
        consultant2.setNic("Nic");
        consultant2.setRole(Role.ADMIN);
        consultant2.setStatus(true);
        when(consultantRepository.save(Mockito.<Consultant>any())).thenReturn(consultant2);
        when(consultantRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Consultant consultant3 = new Consultant();
        consultant3.setCountry("GB");
        consultant3.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant3.setEmail("jane.doe@example.org");
        consultant3.setFirstName("Jane");
        consultant3.setId("42");
        consultant3.setJobType("Job Type");
        consultant3.setLastName("Doe");
        consultant3.setMobile("Mobile");
        consultant3.setNic("Nic");
        consultant3.setRole(Role.ADMIN);
        consultant3.setStatus(true);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Consultant>>any())).thenReturn(consultant3);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId("42");
        user.setPassword("iloveyou");
        user.setRole(Role.ADMIN);
        user.setStatus(true);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult2 = Optional.of(user);
        when(userRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        ResponsePayload actualUpdateConsultantResult = consultantServiceImpl.updateConsultant(new ConsultantDTO());
        assertEquals("Cannot invoke \"java.lang.Iterable.iterator()\" because \"iterable\" is null",
                actualUpdateConsultantResult.getData());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualUpdateConsultantResult.getStatus());
        assertEquals("Internal Server Error", actualUpdateConsultantResult.getMessage());
        verify(consultantRepository).save(Mockito.<Consultant>any());
        verify(consultantRepository).findById(Mockito.<String>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Consultant>>any());
        verify(userRepository).findById(Mockito.<String>any());
    }
    @Test
    void testGetAllConsultants() {
        ArrayList<Consultant> consultantList = new ArrayList<>();
        when(consultantRepository.findAll()).thenReturn(consultantList);
        ResponsePayload actualAllConsultants = consultantServiceImpl.getAllConsultants();
        assertEquals(consultantList, actualAllConsultants.getData());
        assertEquals(HttpStatus.OK, actualAllConsultants.getStatus());
        assertEquals("OK", actualAllConsultants.getMessage());
        verify(consultantRepository).findAll();
    }

    @Test
    void testDeactivateConsultant() {
        Consultant consultant = new Consultant();
        consultant.setCountry("GB");
        consultant.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant.setEmail("jane.doe@example.org");
        consultant.setFirstName("Jane");
        consultant.setId("42");
        consultant.setJobType("Job Type");
        consultant.setLastName("Doe");
        consultant.setMobile("Mobile");
        consultant.setNic("Nic");
        consultant.setRole(Role.ADMIN);
        consultant.setStatus(true);
        Optional<Consultant> ofResult = Optional.of(consultant);

        Consultant consultant2 = new Consultant();
        consultant2.setCountry("GB");
        consultant2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant2.setEmail("jane.doe@example.org");
        consultant2.setFirstName("Jane");
        consultant2.setId("42");
        consultant2.setJobType("Job Type");
        consultant2.setLastName("Doe");
        consultant2.setMobile("Mobile");
        consultant2.setNic("Nic");
        consultant2.setRole(Role.ADMIN);
        consultant2.setStatus(true);
        when(consultantRepository.save(Mockito.<Consultant>any())).thenReturn(consultant2);
        when(consultantRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId("42");
        user.setPassword("iloveyou");
        user.setRole(Role.ADMIN);
        user.setStatus(true);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult2 = Optional.of(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId("42");
        user2.setPassword("iloveyou");
        user2.setRole(Role.ADMIN);
        user2.setStatus(true);
        user2.setTokens(new ArrayList<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        ResponsePayload actualDeactivateConsultantResult = consultantServiceImpl.deactivateConsultant("42", true);
        assertEquals("user successfully updated", actualDeactivateConsultantResult.getData());
        assertEquals(HttpStatus.OK, actualDeactivateConsultantResult.getStatus());
        assertEquals("OK", actualDeactivateConsultantResult.getMessage());
        verify(consultantRepository).save(Mockito.<Consultant>any());
        verify(consultantRepository).findById(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findById(Mockito.<String>any());
    }

    @Test
    void testDeleteConsultant() {
        Consultant consultant = new Consultant();
        consultant.setCountry("GB");
        consultant.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant.setEmail("jane.doe@example.org");
        consultant.setFirstName("Jane");
        consultant.setId("42");
        consultant.setJobType("Job Type");
        consultant.setLastName("Doe");
        consultant.setMobile("Mobile");
        consultant.setNic("Nic");
        consultant.setRole(Role.ADMIN);
        consultant.setStatus(true);
        Optional<Consultant> ofResult = Optional.of(consultant);
        doNothing().when(consultantRepository).delete(Mockito.<Consultant>any());
        when(consultantRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId("42");
        user.setPassword("iloveyou");
        user.setRole(Role.ADMIN);
        user.setStatus(true);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult2 = Optional.of(user);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        ResponsePayload actualDeleteConsultantResult = consultantServiceImpl.deleteConsultant("42");
        assertEquals("user successfully deleted", actualDeleteConsultantResult.getData());
        assertEquals(HttpStatus.OK, actualDeleteConsultantResult.getStatus());
        assertEquals("OK", actualDeleteConsultantResult.getMessage());
        verify(consultantRepository).findById(Mockito.<String>any());
        verify(consultantRepository).delete(Mockito.<Consultant>any());
        verify(userRepository).findById(Mockito.<String>any());
        verify(userRepository).delete(Mockito.<User>any());
    }

    @Test
    void testGetAvailabilityByDate() {
        Consultant consultant = new Consultant();
        consultant.setCountry("GB");
        consultant.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant.setEmail("jane.doe@example.org");
        consultant.setFirstName("Jane");
        consultant.setId("42");
        consultant.setJobType("Job Type");
        consultant.setLastName("Doe");
        consultant.setMobile("Mobile");
        consultant.setNic("Nic");
        consultant.setRole(Role.ADMIN);
        consultant.setStatus(true);

        Availability availability = new Availability();
        availability.setConsultant(consultant);
        availability.setDay(DAYS.SUNDAY);
        availability.setEndTime("End Time");
        availability.setId(1);
        availability.setStartTime("Start Time");
        availability.setStatus(true);
        availability.setTimeSlots("Time Slots");
        when(availabilityRepository.findAvailabilitiesByDayAndConsultant(Mockito.<DAYS>any(), Mockito.<Consultant>any()))
                .thenReturn(availability);

        Consultant consultantId = new Consultant();
        consultantId.setCountry("GB");
        consultantId
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId.setEmail("jane.doe@example.org");
        consultantId.setFirstName("Jane");
        consultantId.setId("42");
        consultantId.setJobType("Job Type");
        consultantId.setLastName("Doe");
        consultantId.setMobile("Mobile");
        consultantId.setNic("Nic");
        consultantId.setRole(Role.ADMIN);
        consultantId.setStatus(true);

        JobSeeker jobSeekerId = new JobSeeker();
        jobSeekerId.setAge(1);
        jobSeekerId.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId.setCv("Cv");
        jobSeekerId.setEmail("jane.doe@example.org");
        jobSeekerId.setFirstName("Jane");
        jobSeekerId.setId("42");
        jobSeekerId.setLastName("Doe");
        jobSeekerId.setMobile("Mobile");
        jobSeekerId.setPreferDestination("Prefer Destination");
        jobSeekerId.setPreferJobType("Prefer Job Type");

        Booking booking = new Booking();
        booking.setConsultantId(consultantId);
        booking.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking.setDate("2020-03-01");
        booking.setId(1);
        booking.setJobSeekerId(jobSeekerId);
        booking.setRejectReason("Just cause");
        booking.setSpecialNote("Special Note");
        booking.setStatus("Status");
        booking.setTime("Time");
        when(bookingRepository.findBookingByDate(Mockito.<String>any())).thenReturn(booking);
        ResponsePayload actualAvailabilityByDate = consultantServiceImpl.getAvailabilityByDate("42", "2020-03-01");
        Object data = actualAvailabilityByDate.getData();
        assertEquals(1, ((Collection<String>) data).size());
        assertEquals("Time Slots", ((List<String>) data).get(0));
        assertEquals(HttpStatus.OK, actualAvailabilityByDate.getStatus());
        assertEquals("OK", actualAvailabilityByDate.getMessage());
        verify(availabilityRepository).findAvailabilitiesByDayAndConsultant(Mockito.<DAYS>any(),
                Mockito.<Consultant>any());
        verify(bookingRepository).findBookingByDate(Mockito.<String>any());
    }

    @Test
    void testGetMyBooking() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findBookingByConsultantIdAndStatus(Mockito.<Consultant>any(), Mockito.<String>any()))
                .thenReturn(bookingList);
        ResponsePayload actualMyBooking = consultantServiceImpl.getMyBooking("42", "Status");
        assertEquals(bookingList, actualMyBooking.getData());
        assertEquals(HttpStatus.OK, actualMyBooking.getStatus());
        assertEquals("OK", actualMyBooking.getMessage());
        verify(bookingRepository).findBookingByConsultantIdAndStatus(Mockito.<Consultant>any(), Mockito.<String>any());
    }

    @Test
    void testGetConsultantDetails() {
        Consultant consultant = new Consultant();
        consultant.setCountry("GB");
        consultant.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultant.setEmail("jane.doe@example.org");
        consultant.setFirstName("Jane");
        consultant.setId("42");
        consultant.setJobType("Job Type");
        consultant.setLastName("Doe");
        consultant.setMobile("Mobile");
        consultant.setNic("Nic");
        consultant.setRole(Role.ADMIN);
        consultant.setStatus(true);
        Optional<Consultant> ofResult = Optional.of(consultant);
        when(consultantRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        ResponsePayload actualConsultantDetails = consultantServiceImpl.getConsultantDetails("42");
        assertSame(consultant, actualConsultantDetails.getData());
        assertEquals(HttpStatus.OK, actualConsultantDetails.getStatus());
        assertEquals("OK", actualConsultantDetails.getMessage());
        verify(consultantRepository).findById(Mockito.<String>any());
    }
}

