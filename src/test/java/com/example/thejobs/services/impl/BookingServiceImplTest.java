package com.example.thejobs.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingDTO;
import com.example.thejobs.dto.BookingResponseDTO;
import com.example.thejobs.dto.JobSeekerDTO;
import com.example.thejobs.dto.enums.Role;
import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.JobSeeker;
import com.example.thejobs.repo.BookingRepository;
import com.example.thejobs.repo.ConsultantRepository;
import com.example.thejobs.repo.JobSeekerRepository;
import com.example.thejobs.services.NotificationService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

@ContextConfiguration(classes = {BookingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BookingServiceImplTest {
    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceImpl bookingServiceImpl;

    @MockBean
    private ConsultantRepository consultantRepository;

    @MockBean
    private JobSeekerRepository jobSeekerRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private NotificationService notificationService;

    @Test
    void testSaveBooking() {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setAge(1);
        jobSeeker.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeeker.setCv("Cv");
        jobSeeker.setEmail("jane.doe@example.org");
        jobSeeker.setFirstName("Jane");
        jobSeeker.setId("42");
        jobSeeker.setLastName("Doe");
        jobSeeker.setMobile("Mobile");
        jobSeeker.setPreferDestination("Prefer Destination");
        jobSeeker.setPreferJobType("Prefer Job Type");
        when(jobSeekerRepository.save(Mockito.<JobSeeker>any())).thenReturn(jobSeeker);

        JobSeeker jobSeeker2 = new JobSeeker();
        jobSeeker2.setAge(1);
        jobSeeker2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeeker2.setCv("Cv");
        jobSeeker2.setEmail("jane.doe@example.org");
        jobSeeker2.setFirstName("Jane");
        jobSeeker2.setId("42");
        jobSeeker2.setLastName("Doe");
        jobSeeker2.setMobile("Mobile");
        jobSeeker2.setPreferDestination("Prefer Destination");
        jobSeeker2.setPreferJobType("Prefer Job Type");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<JobSeeker>>any())).thenReturn(jobSeeker2);

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
        when(consultantRepository.findByCountryAndJobType(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(consultant);

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
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking);
        when(notificationService.sendBookingPendingEmail(Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
                .thenReturn(new ResponsePayload());
        ResponsePayload actualSaveBookingResult = bookingServiceImpl.saveBooking(new JobSeekerDTO());
        assertEquals("Not Trigger booking save Email", actualSaveBookingResult.getData());
        assertEquals(HttpStatus.BAD_REQUEST, actualSaveBookingResult.getStatus());
        assertEquals("Bad Request", actualSaveBookingResult.getMessage());
        verify(jobSeekerRepository).save(Mockito.<JobSeeker>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<JobSeeker>>any());
        verify(consultantRepository).findByCountryAndJobType(Mockito.<String>any(), Mockito.<String>any());
        verify(bookingRepository).save(Mockito.<Booking>any());
        verify(notificationService).sendBookingPendingEmail(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    }


    @Test
    @Disabled("TODO: Complete this test")
    void testAcceptBooking() {

        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setAge(1);
        jobSeeker.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeeker.setCv("Cv");
        jobSeeker.setEmail("jane.doe@example.org");
        jobSeeker.setFirstName("Jane");
        jobSeeker.setId("42");
        jobSeeker.setLastName("Doe");
        jobSeeker.setMobile("Mobile");
        jobSeeker.setPreferDestination("Prefer Destination");
        jobSeeker.setPreferJobType("Prefer Job Type");
        Optional<JobSeeker> ofResult = Optional.of(jobSeeker);
        when(jobSeekerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Consultant consultantId = new Consultant();
        consultantId.setCountry("GB");
        consultantId.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
        Optional<Booking> ofResult2 = Optional.of(booking);

        Consultant consultantId2 = new Consultant();
        consultantId2.setCountry("GB");
        consultantId2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId2.setEmail("jane.doe@example.org");
        consultantId2.setFirstName("Jane");
        consultantId2.setId("42");
        consultantId2.setJobType("Job Type");
        consultantId2.setLastName("Doe");
        consultantId2.setMobile("Mobile");
        consultantId2.setNic("Nic");
        consultantId2.setRole(Role.ADMIN);
        consultantId2.setStatus(true);

        JobSeeker jobSeekerId2 = new JobSeeker();
        jobSeekerId2.setAge(1);
        jobSeekerId2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId2.setCv("Cv");
        jobSeekerId2.setEmail("jane.doe@example.org");
        jobSeekerId2.setFirstName("Jane");
        jobSeekerId2.setId("42");
        jobSeekerId2.setLastName("Doe");
        jobSeekerId2.setMobile("Mobile");
        jobSeekerId2.setPreferDestination("Prefer Destination");
        jobSeekerId2.setPreferJobType("Prefer Job Type");

        Booking booking2 = new Booking();
        booking2.setConsultantId(consultantId2);
        booking2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking2.setDate("2020-03-01");
        booking2.setId(1);
        booking2.setJobSeekerId(jobSeekerId2);
        booking2.setRejectReason("Just cause");
        booking2.setSpecialNote("Special Note");
        booking2.setStatus("Status");
        booking2.setTime("Time");
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult2);
        bookingServiceImpl.acceptBooking(new BookingDTO());
    }


    @Test
    @Disabled("TODO: Complete this test")
    void testAcceptBooking2() {


        Optional<JobSeeker> emptyResult = Optional.empty();
        when(jobSeekerRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

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
        Optional<Booking> ofResult = Optional.of(booking);

        Consultant consultantId2 = new Consultant();
        consultantId2.setCountry("GB");
        consultantId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId2.setEmail("jane.doe@example.org");
        consultantId2.setFirstName("Jane");
        consultantId2.setId("42");
        consultantId2.setJobType("Job Type");
        consultantId2.setLastName("Doe");
        consultantId2.setMobile("Mobile");
        consultantId2.setNic("Nic");
        consultantId2.setRole(Role.ADMIN);
        consultantId2.setStatus(true);

        JobSeeker jobSeekerId2 = new JobSeeker();
        jobSeekerId2.setAge(1);
        jobSeekerId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId2.setCv("Cv");
        jobSeekerId2.setEmail("jane.doe@example.org");
        jobSeekerId2.setFirstName("Jane");
        jobSeekerId2.setId("42");
        jobSeekerId2.setLastName("Doe");
        jobSeekerId2.setMobile("Mobile");
        jobSeekerId2.setPreferDestination("Prefer Destination");
        jobSeekerId2.setPreferJobType("Prefer Job Type");

        Booking booking2 = new Booking();
        booking2.setConsultantId(consultantId2);
        booking2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking2.setDate("2020-03-01");
        booking2.setId(1);
        booking2.setJobSeekerId(jobSeekerId2);
        booking2.setRejectReason("Just cause");
        booking2.setSpecialNote("Special Note");
        booking2.setStatus("Status");
        booking2.setTime("Time");
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        bookingServiceImpl.acceptBooking(new BookingDTO());
    }


    @Test
    void testAcceptBooking3() {
        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getEmail()).thenReturn("jane.doe@example.org");
        when(jobSeeker.getLastName()).thenReturn("Doe");
        when(jobSeeker.getFirstName()).thenReturn("Jane");
        doNothing().when(jobSeeker).setAge(anyInt());
        doNothing().when(jobSeeker).setCreatedDate(Mockito.<Date>any());
        doNothing().when(jobSeeker).setCv(Mockito.<String>any());
        doNothing().when(jobSeeker).setEmail(Mockito.<String>any());
        doNothing().when(jobSeeker).setFirstName(Mockito.<String>any());
        doNothing().when(jobSeeker).setId(Mockito.<String>any());
        doNothing().when(jobSeeker).setLastName(Mockito.<String>any());
        doNothing().when(jobSeeker).setMobile(Mockito.<String>any());
        doNothing().when(jobSeeker).setPreferDestination(Mockito.<String>any());
        doNothing().when(jobSeeker).setPreferJobType(Mockito.<String>any());
        jobSeeker.setAge(1);
        jobSeeker.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeeker.setCv("Cv");
        jobSeeker.setEmail("jane.doe@example.org");
        jobSeeker.setFirstName("Jane");
        jobSeeker.setId("42");
        jobSeeker.setLastName("Doe");
        jobSeeker.setMobile("Mobile");
        jobSeeker.setPreferDestination("Prefer Destination");
        jobSeeker.setPreferJobType("Prefer Job Type");
        Optional<JobSeeker> ofResult = Optional.of(jobSeeker);
        when(jobSeekerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

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

        JobSeeker jobSeeker2 = new JobSeeker();
        jobSeeker2.setAge(1);
        jobSeeker2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeeker2.setCv("Cv");
        jobSeeker2.setEmail("jane.doe@example.org");
        jobSeeker2.setFirstName("Jane");
        jobSeeker2.setId("42");
        jobSeeker2.setLastName("Doe");
        jobSeeker2.setMobile("Mobile");
        jobSeeker2.setPreferDestination("Prefer Destination");
        jobSeeker2.setPreferJobType("Prefer Job Type");
        Booking booking = mock(Booking.class);
        when(booking.getSpecialNote()).thenReturn("Special Note");
        when(booking.getTime()).thenReturn("Time");
        when(booking.getDate()).thenReturn("2020-03-01");
        when(booking.getJobSeekerId()).thenReturn(jobSeeker2);
        doNothing().when(booking).setConsultantId(Mockito.<Consultant>any());
        doNothing().when(booking).setCreatedDate(Mockito.<Date>any());
        doNothing().when(booking).setDate(Mockito.<String>any());
        doNothing().when(booking).setId(Mockito.<Integer>any());
        doNothing().when(booking).setJobSeekerId(Mockito.<JobSeeker>any());
        doNothing().when(booking).setRejectReason(Mockito.<String>any());
        doNothing().when(booking).setSpecialNote(Mockito.<String>any());
        doNothing().when(booking).setStatus(Mockito.<String>any());
        doNothing().when(booking).setTime(Mockito.<String>any());
        booking.setConsultantId(consultantId);
        booking.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking.setDate("2020-03-01");
        booking.setId(1);
        booking.setJobSeekerId(jobSeekerId);
        booking.setRejectReason("Just cause");
        booking.setSpecialNote("Special Note");
        booking.setStatus("Status");
        booking.setTime("Time");
        Optional<Booking> ofResult2 = Optional.of(booking);

        Consultant consultantId2 = new Consultant();
        consultantId2.setCountry("GB");
        consultantId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId2.setEmail("jane.doe@example.org");
        consultantId2.setFirstName("Jane");
        consultantId2.setId("42");
        consultantId2.setJobType("Job Type");
        consultantId2.setLastName("Doe");
        consultantId2.setMobile("Mobile");
        consultantId2.setNic("Nic");
        consultantId2.setRole(Role.ADMIN);
        consultantId2.setStatus(true);

        JobSeeker jobSeekerId2 = new JobSeeker();
        jobSeekerId2.setAge(1);
        jobSeekerId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId2.setCv("Cv");
        jobSeekerId2.setEmail("jane.doe@example.org");
        jobSeekerId2.setFirstName("Jane");
        jobSeekerId2.setId("42");
        jobSeekerId2.setLastName("Doe");
        jobSeekerId2.setMobile("Mobile");
        jobSeekerId2.setPreferDestination("Prefer Destination");
        jobSeekerId2.setPreferJobType("Prefer Job Type");

        Booking booking2 = new Booking();
        booking2.setConsultantId(consultantId2);
        booking2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking2.setDate("2020-03-01");
        booking2.setId(1);
        booking2.setJobSeekerId(jobSeekerId2);
        booking2.setRejectReason("Just cause");
        booking2.setSpecialNote("Special Note");
        booking2.setStatus("Status");
        booking2.setTime("Time");
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult2);
        when(notificationService.sendBookingConfirmEmail(Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
                .thenReturn(new ResponsePayload());
        ResponsePayload actualAcceptBookingResult = bookingServiceImpl.acceptBooking(new BookingDTO());
        assertEquals("Not Trigger booking Confirmatio email", actualAcceptBookingResult.getData());
        assertEquals(HttpStatus.BAD_REQUEST, actualAcceptBookingResult.getStatus());
        assertEquals("Bad Request", actualAcceptBookingResult.getMessage());
        verify(jobSeekerRepository).findById(Mockito.<String>any());
        verify(jobSeeker).getEmail();
        verify(jobSeeker).getFirstName();
        verify(jobSeeker).getLastName();
        verify(jobSeeker).setAge(anyInt());
        verify(jobSeeker).setCreatedDate(Mockito.<Date>any());
        verify(jobSeeker).setCv(Mockito.<String>any());
        verify(jobSeeker).setEmail(Mockito.<String>any());
        verify(jobSeeker).setFirstName(Mockito.<String>any());
        verify(jobSeeker).setId(Mockito.<String>any());
        verify(jobSeeker).setLastName(Mockito.<String>any());
        verify(jobSeeker).setMobile(Mockito.<String>any());
        verify(jobSeeker).setPreferDestination(Mockito.<String>any());
        verify(jobSeeker).setPreferJobType(Mockito.<String>any());
        verify(bookingRepository).save(Mockito.<Booking>any());
        verify(bookingRepository).findById(Mockito.<Integer>any());
        verify(booking).getJobSeekerId();
        verify(booking).getDate();
        verify(booking).getSpecialNote();
        verify(booking).getTime();
        verify(booking).setConsultantId(Mockito.<Consultant>any());
        verify(booking).setCreatedDate(Mockito.<Date>any());
        verify(booking, atLeast(1)).setDate(Mockito.<String>any());
        verify(booking).setId(Mockito.<Integer>any());
        verify(booking).setJobSeekerId(Mockito.<JobSeeker>any());
        verify(booking).setRejectReason(Mockito.<String>any());
        verify(booking, atLeast(1)).setSpecialNote(Mockito.<String>any());
        verify(booking, atLeast(1)).setStatus(Mockito.<String>any());
        verify(booking, atLeast(1)).setTime(Mockito.<String>any());
        verify(notificationService).sendBookingConfirmEmail(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    }


    @Test
    void testAcceptBooking4() {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setAge(1);
        jobSeeker.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeeker.setCv("Cv");
        jobSeeker.setEmail("jane.doe@example.org");
        jobSeeker.setFirstName("Jane");
        jobSeeker.setId("42");
        jobSeeker.setLastName("Doe");
        jobSeeker.setMobile("Mobile");
        jobSeeker.setPreferDestination("Prefer Destination");
        jobSeeker.setPreferJobType("Prefer Job Type");
        Optional<JobSeeker> ofResult = Optional.of(jobSeeker);
        when(jobSeekerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

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
        Optional<Booking> ofResult2 = Optional.of(booking);

        Consultant consultantId2 = new Consultant();
        consultantId2.setCountry("GB");
        consultantId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId2.setEmail("jane.doe@example.org");
        consultantId2.setFirstName("Jane");
        consultantId2.setId("42");
        consultantId2.setJobType("Job Type");
        consultantId2.setLastName("Doe");
        consultantId2.setMobile("Mobile");
        consultantId2.setNic("Nic");
        consultantId2.setRole(Role.ADMIN);
        consultantId2.setStatus(true);

        JobSeeker jobSeekerId2 = new JobSeeker();
        jobSeekerId2.setAge(1);
        jobSeekerId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId2.setCv("Cv");
        jobSeekerId2.setEmail("jane.doe@example.org");
        jobSeekerId2.setFirstName("Jane");
        jobSeekerId2.setId("42");
        jobSeekerId2.setLastName("Doe");
        jobSeekerId2.setMobile("Mobile");
        jobSeekerId2.setPreferDestination("Prefer Destination");
        jobSeekerId2.setPreferJobType("Prefer Job Type");

        Booking booking2 = new Booking();
        booking2.setConsultantId(consultantId2);
        booking2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking2.setDate("2020-03-01");
        booking2.setId(1);
        booking2.setJobSeekerId(jobSeekerId2);
        booking2.setRejectReason("Just cause");
        booking2.setSpecialNote("Special Note");
        booking2.setStatus("Status");
        booking2.setTime("Time");
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult2);
        when(notificationService.sendBookingConfirmEmail(Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
                .thenReturn(new ResponsePayload());
        BookingDTO bookingDTO = BookingDTO.builder()
                .date("2020-03-01")
                .id(1)
                .rejectReason("Just cause")
                .specialNote("Special Note")
                .status("Status")
                .time("Time")
                .build();
        ResponsePayload actualAcceptBookingResult = bookingServiceImpl.acceptBooking(bookingDTO);
        assertEquals("Not Trigger booking Confirmatio email", actualAcceptBookingResult.getData());
        assertEquals(HttpStatus.BAD_REQUEST, actualAcceptBookingResult.getStatus());
        assertEquals("Bad Request", actualAcceptBookingResult.getMessage());
        verify(jobSeekerRepository).findById(Mockito.<String>any());
        verify(bookingRepository).save(Mockito.<Booking>any());
        verify(bookingRepository).findById(Mockito.<Integer>any());
        verify(notificationService).sendBookingConfirmEmail(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    }


    @Test
    void testRejectBooking() {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Consultant consultantId2 = new Consultant();
        consultantId2.setCountry("GB");
        consultantId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId2.setEmail("jane.doe@example.org");
        consultantId2.setFirstName("Jane");
        consultantId2.setId("42");
        consultantId2.setJobType("Job Type");
        consultantId2.setLastName("Doe");
        consultantId2.setMobile("Mobile");
        consultantId2.setNic("Nic");
        consultantId2.setRole(Role.ADMIN);
        consultantId2.setStatus(true);

        JobSeeker jobSeekerId2 = new JobSeeker();
        jobSeekerId2.setAge(1);
        jobSeekerId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId2.setCv("Cv");
        jobSeekerId2.setEmail("jane.doe@example.org");
        jobSeekerId2.setFirstName("Jane");
        jobSeekerId2.setId("42");
        jobSeekerId2.setLastName("Doe");
        jobSeekerId2.setMobile("Mobile");
        jobSeekerId2.setPreferDestination("Prefer Destination");
        jobSeekerId2.setPreferJobType("Prefer Job Type");

        Booking booking2 = new Booking();
        booking2.setConsultantId(consultantId2);
        booking2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking2.setDate("2020-03-01");
        booking2.setId(1);
        booking2.setJobSeekerId(jobSeekerId2);
        booking2.setRejectReason("Just cause");
        booking2.setSpecialNote("Special Note");
        booking2.setStatus("Status");
        booking2.setTime("Time");
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ResponsePayload actualRejectBookingResult = bookingServiceImpl.rejectBooking(new BookingDTO());
        assertEquals("Booking Rejected", actualRejectBookingResult.getData());
        assertEquals(HttpStatus.OK, actualRejectBookingResult.getStatus());
        assertEquals("OK", actualRejectBookingResult.getMessage());
        verify(bookingRepository).save(Mockito.<Booking>any());
        verify(bookingRepository).findById(Mockito.<Integer>any());
    }


    @Test
    void testRejectBooking2() {
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
        Booking booking = mock(Booking.class);
        doNothing().when(booking).setConsultantId(Mockito.<Consultant>any());
        doNothing().when(booking).setCreatedDate(Mockito.<Date>any());
        doNothing().when(booking).setDate(Mockito.<String>any());
        doNothing().when(booking).setId(Mockito.<Integer>any());
        doNothing().when(booking).setJobSeekerId(Mockito.<JobSeeker>any());
        doNothing().when(booking).setRejectReason(Mockito.<String>any());
        doNothing().when(booking).setSpecialNote(Mockito.<String>any());
        doNothing().when(booking).setStatus(Mockito.<String>any());
        doNothing().when(booking).setTime(Mockito.<String>any());
        booking.setConsultantId(consultantId);
        booking.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking.setDate("2020-03-01");
        booking.setId(1);
        booking.setJobSeekerId(jobSeekerId);
        booking.setRejectReason("Just cause");
        booking.setSpecialNote("Special Note");
        booking.setStatus("Status");
        booking.setTime("Time");
        Optional<Booking> ofResult = Optional.of(booking);

        Consultant consultantId2 = new Consultant();
        consultantId2.setCountry("GB");
        consultantId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId2.setEmail("jane.doe@example.org");
        consultantId2.setFirstName("Jane");
        consultantId2.setId("42");
        consultantId2.setJobType("Job Type");
        consultantId2.setLastName("Doe");
        consultantId2.setMobile("Mobile");
        consultantId2.setNic("Nic");
        consultantId2.setRole(Role.ADMIN);
        consultantId2.setStatus(true);

        JobSeeker jobSeekerId2 = new JobSeeker();
        jobSeekerId2.setAge(1);
        jobSeekerId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId2.setCv("Cv");
        jobSeekerId2.setEmail("jane.doe@example.org");
        jobSeekerId2.setFirstName("Jane");
        jobSeekerId2.setId("42");
        jobSeekerId2.setLastName("Doe");
        jobSeekerId2.setMobile("Mobile");
        jobSeekerId2.setPreferDestination("Prefer Destination");
        jobSeekerId2.setPreferJobType("Prefer Job Type");

        Booking booking2 = new Booking();
        booking2.setConsultantId(consultantId2);
        booking2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking2.setDate("2020-03-01");
        booking2.setId(1);
        booking2.setJobSeekerId(jobSeekerId2);
        booking2.setRejectReason("Just cause");
        booking2.setSpecialNote("Special Note");
        booking2.setStatus("Status");
        booking2.setTime("Time");
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        BookingDTO bookingDTO = BookingDTO.builder()
                .date("2020-03-01")
                .id(1)
                .rejectReason("Just cause")
                .specialNote("Special Note")
                .status("Status")
                .time("Time")
                .build();
        ResponsePayload actualRejectBookingResult = bookingServiceImpl.rejectBooking(bookingDTO);
        assertEquals("Booking Rejected", actualRejectBookingResult.getData());
        assertEquals(HttpStatus.OK, actualRejectBookingResult.getStatus());
        assertEquals("OK", actualRejectBookingResult.getMessage());
        verify(bookingRepository).save(Mockito.<Booking>any());
        verify(bookingRepository).findById(Mockito.<Integer>any());
        verify(booking).setConsultantId(Mockito.<Consultant>any());
        verify(booking).setCreatedDate(Mockito.<Date>any());
        verify(booking, atLeast(1)).setDate(Mockito.<String>any());
        verify(booking).setId(Mockito.<Integer>any());
        verify(booking).setJobSeekerId(Mockito.<JobSeeker>any());
        verify(booking).setRejectReason(Mockito.<String>any());
        verify(booking, atLeast(1)).setSpecialNote(Mockito.<String>any());
        verify(booking, atLeast(1)).setStatus(Mockito.<String>any());
        verify(booking, atLeast(1)).setTime(Mockito.<String>any());
    }


    @Test
    void testCompleteBooking() {
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
        Optional<Booking> ofResult = Optional.of(booking);

        Consultant consultantId2 = new Consultant();
        consultantId2.setCountry("GB");
        consultantId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId2.setEmail("jane.doe@example.org");
        consultantId2.setFirstName("Jane");
        consultantId2.setId("42");
        consultantId2.setJobType("Job Type");
        consultantId2.setLastName("Doe");
        consultantId2.setMobile("Mobile");
        consultantId2.setNic("Nic");
        consultantId2.setRole(Role.ADMIN);
        consultantId2.setStatus(true);

        JobSeeker jobSeekerId2 = new JobSeeker();
        jobSeekerId2.setAge(1);
        jobSeekerId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId2.setCv("Cv");
        jobSeekerId2.setEmail("jane.doe@example.org");
        jobSeekerId2.setFirstName("Jane");
        jobSeekerId2.setId("42");
        jobSeekerId2.setLastName("Doe");
        jobSeekerId2.setMobile("Mobile");
        jobSeekerId2.setPreferDestination("Prefer Destination");
        jobSeekerId2.setPreferJobType("Prefer Job Type");

        Booking booking2 = new Booking();
        booking2.setConsultantId(consultantId2);
        booking2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking2.setDate("2020-03-01");
        booking2.setId(1);
        booking2.setJobSeekerId(jobSeekerId2);
        booking2.setRejectReason("Just cause");
        booking2.setSpecialNote("Special Note");
        booking2.setStatus("Status");
        booking2.setTime("Time");
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ResponsePayload actualCompleteBookingResult = bookingServiceImpl.completeBooking(1);
        assertEquals("Booking Completed", actualCompleteBookingResult.getData());
        assertEquals(HttpStatus.OK, actualCompleteBookingResult.getStatus());
        assertEquals("OK", actualCompleteBookingResult.getMessage());
        verify(bookingRepository).save(Mockito.<Booking>any());
        verify(bookingRepository).findById(Mockito.<Integer>any());
    }


    @Test
    void testCompleteBooking2() {
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
        Booking booking = mock(Booking.class);
        doNothing().when(booking).setConsultantId(Mockito.<Consultant>any());
        doNothing().when(booking).setCreatedDate(Mockito.<Date>any());
        doNothing().when(booking).setDate(Mockito.<String>any());
        doNothing().when(booking).setId(Mockito.<Integer>any());
        doNothing().when(booking).setJobSeekerId(Mockito.<JobSeeker>any());
        doNothing().when(booking).setRejectReason(Mockito.<String>any());
        doNothing().when(booking).setSpecialNote(Mockito.<String>any());
        doNothing().when(booking).setStatus(Mockito.<String>any());
        doNothing().when(booking).setTime(Mockito.<String>any());
        booking.setConsultantId(consultantId);
        booking.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking.setDate("2020-03-01");
        booking.setId(1);
        booking.setJobSeekerId(jobSeekerId);
        booking.setRejectReason("Just cause");
        booking.setSpecialNote("Special Note");
        booking.setStatus("Status");
        booking.setTime("Time");
        Optional<Booking> ofResult = Optional.of(booking);

        Consultant consultantId2 = new Consultant();
        consultantId2.setCountry("GB");
        consultantId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId2.setEmail("jane.doe@example.org");
        consultantId2.setFirstName("Jane");
        consultantId2.setId("42");
        consultantId2.setJobType("Job Type");
        consultantId2.setLastName("Doe");
        consultantId2.setMobile("Mobile");
        consultantId2.setNic("Nic");
        consultantId2.setRole(Role.ADMIN);
        consultantId2.setStatus(true);

        JobSeeker jobSeekerId2 = new JobSeeker();
        jobSeekerId2.setAge(1);
        jobSeekerId2
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId2.setCv("Cv");
        jobSeekerId2.setEmail("jane.doe@example.org");
        jobSeekerId2.setFirstName("Jane");
        jobSeekerId2.setId("42");
        jobSeekerId2.setLastName("Doe");
        jobSeekerId2.setMobile("Mobile");
        jobSeekerId2.setPreferDestination("Prefer Destination");
        jobSeekerId2.setPreferJobType("Prefer Job Type");

        Booking booking2 = new Booking();
        booking2.setConsultantId(consultantId2);
        booking2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking2.setDate("2020-03-01");
        booking2.setId(1);
        booking2.setJobSeekerId(jobSeekerId2);
        booking2.setRejectReason("Just cause");
        booking2.setSpecialNote("Special Note");
        booking2.setStatus("Status");
        booking2.setTime("Time");
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        ResponsePayload actualCompleteBookingResult = bookingServiceImpl.completeBooking(1);
        assertEquals("Booking Completed", actualCompleteBookingResult.getData());
        assertEquals(HttpStatus.OK, actualCompleteBookingResult.getStatus());
        assertEquals("OK", actualCompleteBookingResult.getMessage());
        verify(bookingRepository).save(Mockito.<Booking>any());
        verify(bookingRepository).findById(Mockito.<Integer>any());
        verify(booking).setConsultantId(Mockito.<Consultant>any());
        verify(booking).setCreatedDate(Mockito.<Date>any());
        verify(booking, atLeast(1)).setDate(Mockito.<String>any());
        verify(booking).setId(Mockito.<Integer>any());
        verify(booking).setJobSeekerId(Mockito.<JobSeeker>any());
        verify(booking).setRejectReason(Mockito.<String>any());
        verify(booking).setSpecialNote(Mockito.<String>any());
        verify(booking, atLeast(1)).setStatus(Mockito.<String>any());
        verify(booking, atLeast(1)).setTime(Mockito.<String>any());
    }

    @Test
    void testGetAllBooking() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findBookingByStatus(Mockito.<String>any())).thenReturn(bookingList);
        ResponsePayload actualAllBooking = bookingServiceImpl.getAllBooking("Status");
        assertEquals(bookingList, actualAllBooking.getData());
        assertEquals(HttpStatus.OK, actualAllBooking.getStatus());
        assertEquals("OK", actualAllBooking.getMessage());
        verify(bookingRepository).findBookingByStatus(Mockito.<String>any());
    }


    @Test
    void testGetAllBooking2() {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(null);

        Consultant consultantId = new Consultant();
        consultantId.setCountry("GB");
        consultantId
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId.setEmail("jane.doe@example.org");
        consultantId.setFirstName("Jane");
        consultantId.setId("42");
        consultantId.setJobType("All");
        consultantId.setLastName("Doe");
        consultantId.setMobile("All");
        consultantId.setNic("All");
        consultantId.setRole(Role.ADMIN);
        consultantId.setStatus(true);

        JobSeeker jobSeekerId = new JobSeeker();
        jobSeekerId.setAge(1);
        jobSeekerId.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId.setCv("All");
        jobSeekerId.setEmail("jane.doe@example.org");
        jobSeekerId.setFirstName("Jane");
        jobSeekerId.setId("42");
        jobSeekerId.setLastName("Doe");
        jobSeekerId.setMobile("All");
        jobSeekerId.setPreferDestination("All");
        jobSeekerId.setPreferJobType("All");

        Booking booking = new Booking();
        booking.setConsultantId(consultantId);
        booking.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking.setDate("2020-03-01");
        booking.setId(1);
        booking.setJobSeekerId(jobSeekerId);
        booking.setRejectReason("Just cause");
        booking.setSpecialNote("All");
        booking.setStatus("All");
        booking.setTime("All");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findBookingByStatus(Mockito.<String>any())).thenReturn(bookingList);
        ResponsePayload actualAllBooking = bookingServiceImpl.getAllBooking("Status");
        Object data = actualAllBooking.getData();
        assertEquals(1, ((Collection<BookingResponseDTO>) data).size());
        assertEquals(HttpStatus.OK, actualAllBooking.getStatus());
        assertEquals("OK", actualAllBooking.getMessage());
        BookingResponseDTO getResult = ((List<BookingResponseDTO>) data).get(0);
        assertNull(getResult.getBooking());
        assertNull(getResult.getJobSeeker());
        assertNull(getResult.getConsultant());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(bookingRepository).findBookingByStatus(Mockito.<String>any());
    }


    @Test
    void testGetAllBooking3() {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(null);

        Consultant consultantId = new Consultant();
        consultantId.setCountry("GB");
        consultantId
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantId.setEmail("jane.doe@example.org");
        consultantId.setFirstName("Jane");
        consultantId.setId("42");
        consultantId.setJobType("All");
        consultantId.setLastName("Doe");
        consultantId.setMobile("All");
        consultantId.setNic("All");
        consultantId.setRole(Role.ADMIN);
        consultantId.setStatus(true);

        JobSeeker jobSeekerId = new JobSeeker();
        jobSeekerId.setAge(1);
        jobSeekerId.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeekerId.setCv("All");
        jobSeekerId.setEmail("jane.doe@example.org");
        jobSeekerId.setFirstName("Jane");
        jobSeekerId.setId("42");
        jobSeekerId.setLastName("Doe");
        jobSeekerId.setMobile("All");
        jobSeekerId.setPreferDestination("All");
        jobSeekerId.setPreferJobType("All");

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

        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setAge(1);
        jobSeeker.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        jobSeeker.setCv("Cv");
        jobSeeker.setEmail("jane.doe@example.org");
        jobSeeker.setFirstName("Jane");
        jobSeeker.setId("42");
        jobSeeker.setLastName("Doe");
        jobSeeker.setMobile("Mobile");
        jobSeeker.setPreferDestination("Prefer Destination");
        jobSeeker.setPreferJobType("Prefer Job Type");
        Booking booking = mock(Booking.class);
        when(booking.getConsultantId()).thenReturn(consultant);
        when(booking.getJobSeekerId()).thenReturn(jobSeeker);
        doNothing().when(booking).setConsultantId(Mockito.<Consultant>any());
        doNothing().when(booking).setCreatedDate(Mockito.<Date>any());
        doNothing().when(booking).setDate(Mockito.<String>any());
        doNothing().when(booking).setId(Mockito.<Integer>any());
        doNothing().when(booking).setJobSeekerId(Mockito.<JobSeeker>any());
        doNothing().when(booking).setRejectReason(Mockito.<String>any());
        doNothing().when(booking).setSpecialNote(Mockito.<String>any());
        doNothing().when(booking).setStatus(Mockito.<String>any());
        doNothing().when(booking).setTime(Mockito.<String>any());
        booking.setConsultantId(consultantId);
        booking.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        booking.setDate("2020-03-01");
        booking.setId(1);
        booking.setJobSeekerId(jobSeekerId);
        booking.setRejectReason("Just cause");
        booking.setSpecialNote("All");
        booking.setStatus("All");
        booking.setTime("All");

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findBookingByStatus(Mockito.<String>any())).thenReturn(bookingList);
        ResponsePayload actualAllBooking = bookingServiceImpl.getAllBooking("Status");
        Object data = actualAllBooking.getData();
        assertEquals(1, ((Collection<BookingResponseDTO>) data).size());
        assertEquals(HttpStatus.OK, actualAllBooking.getStatus());
        assertEquals("OK", actualAllBooking.getMessage());
        BookingResponseDTO getResult = ((List<BookingResponseDTO>) data).get(0);
        assertNull(getResult.getBooking());
        assertNull(getResult.getJobSeeker());
        assertNull(getResult.getConsultant());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(bookingRepository).findBookingByStatus(Mockito.<String>any());
        verify(booking).getConsultantId();
        verify(booking).getJobSeekerId();
        verify(booking).setConsultantId(Mockito.<Consultant>any());
        verify(booking).setCreatedDate(Mockito.<Date>any());
        verify(booking).setDate(Mockito.<String>any());
        verify(booking).setId(Mockito.<Integer>any());
        verify(booking).setJobSeekerId(Mockito.<JobSeeker>any());
        verify(booking).setRejectReason(Mockito.<String>any());
        verify(booking).setSpecialNote(Mockito.<String>any());
        verify(booking).setStatus(Mockito.<String>any());
        verify(booking).setTime(Mockito.<String>any());
    }
}

