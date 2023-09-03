package com.example.thejobs.services.impl;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingDTO;
import com.example.thejobs.dto.BookingResponseDTO;
import com.example.thejobs.dto.JobSeekerDTO;
import com.example.thejobs.dto.consultant.ConsultantDTO;
import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.JobSeeker;
import com.example.thejobs.repo.BookingRepository;
import com.example.thejobs.repo.ConsultantRepository;
import com.example.thejobs.repo.JobSeekerRepository;
import com.example.thejobs.services.BookingService;
import com.example.thejobs.services.NotificationService;
import com.example.thejobs.utility.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final JobSeekerRepository jobSeekerRepository;
    private final ModelMapper modelMapper;
    private final ConsultantRepository consultantRepository;
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public ResponsePayload saveBooking(JobSeekerDTO jobSeekerDTO) {
        JobSeeker jobSeeker = modelMapper.map(jobSeekerDTO, com.example.thejobs.entity.JobSeeker.class);
        jobSeeker.setId(UUID.randomUUID().toString());
        jobSeeker.setCreatedDate(Calendar.getInstance().getTime());
        JobSeeker saveJobSeeker = jobSeekerRepository.save(jobSeeker);
        Consultant consultant = consultantRepository.findByCountryAndJobType(jobSeeker.getPreferDestination(), jobSeekerDTO.getPreferJobType());
        if (consultant == null) {
            consultant = consultantRepository.findByCountry(consultant.getCountry());
        }

        Booking booking = com.example.thejobs.entity.Booking.builder()
                .jobSeekerId(saveJobSeeker)
                .consultantId(consultant)
                .status("PENDING")
                .build();

        bookingRepository.save(booking);

        ResponsePayload responsePayload = sendBookingPendingEmail(saveJobSeeker);

        if (responsePayload.getStatus() == HttpStatus.OK) {
            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Booking Added", HttpStatus.OK);
        } else {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Not Trigger booking save Email", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponsePayload acceptBooking(BookingDTO bookingDTO) {
        Optional<Booking> isBooking = bookingRepository.findById(bookingDTO.getId());
        if (isBooking.isPresent()) {
            Booking booking = isBooking.get();
            JobSeeker jobSeeker = jobSeekerRepository.findById(booking.getJobSeekerId().getId()).get();
            booking.setDate(bookingDTO.getDate());
            booking.setStatus("APPROVED");
            booking.setTime(bookingDTO.getTime());
            booking.setSpecialNote(bookingDTO.getSpecialNote());

            bookingRepository.save(booking);

            ResponsePayload responsePayload = sendBookingConfirmEmail(jobSeeker, booking);

            if (responsePayload.getStatus() == HttpStatus.OK) {
                return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Booking Accepted", HttpStatus.OK);
            } else {
                return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Not Trigger booking Confirmatio email", HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Booking id does not exists", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponsePayload rejectBooking(BookingDTO bookingDTO) {
        Optional<Booking> isBooking = bookingRepository.findById(bookingDTO.getId());

        if (isBooking.isPresent()) {
            Booking booking = isBooking.get();

            booking.setDate("");
            booking.setStatus("REJECTED");
            booking.setTime("");
            booking.setSpecialNote(bookingDTO.getSpecialNote());

            bookingRepository.save(booking);
        } else {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Booking id does not exists", HttpStatus.OK);
        }
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Booking Rejected", HttpStatus.OK);

    }

    @Override
    public ResponsePayload completeBooking(int id) {
        Optional<Booking> isBooking = bookingRepository.findById(id);

        if (isBooking.isPresent()) {
            Booking booking = isBooking.get();
            booking.setDate("");
            booking.setTime("");
            booking.setStatus("COMPLETED");
            bookingRepository.save(booking);
        } else {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Booking id does not exists", HttpStatus.OK);
        }
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Booking Completed", HttpStatus.OK);
    }

    @Override
    public ResponsePayload getAllBooking(String status) {
        List<Booking> all;
        if(status.equals("All")){
            all = bookingRepository.findAll();
        }else{
            all = bookingRepository.findBookingByStatus(status);
        }

        List<BookingResponseDTO> bookingResponseDTO = new ArrayList<>();
        for (Booking dto : all) {
            Consultant consultant = dto.getConsultantId();
            JobSeeker jobSeeker = dto.getJobSeekerId();
            ConsultantDTO consultantDTO = modelMapper.map(consultant, ConsultantDTO.class);
            BookingDTO booking = modelMapper.map(dto, BookingDTO.class);
            JobSeekerDTO jobSeekerDTO = modelMapper.map(jobSeeker, JobSeekerDTO.class);
            BookingResponseDTO brd = BookingResponseDTO.builder()
                    .consultant(consultantDTO)
                    .booking(booking)
                    .jobSeeker(jobSeekerDTO)
                    .build();

            bookingResponseDTO.add(brd);
        }
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), bookingResponseDTO, HttpStatus.OK);

    }

    private ResponsePayload sendBookingConfirmEmail(JobSeeker jobSeeker, Booking booking) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", jobSeeker.getFirstName().toUpperCase() + " " + jobSeeker.getLastName().toUpperCase());
        model.put("date", Utility.formatDateTime(booking.getDate(), "yyyy-MM-dd", "dd MMM, yyyy"));
        model.put("time", Utility.formatDateTime(booking.getTime(), "HH:mm", "h . mm a"));
        model.put("additionDetails", booking.getSpecialNote());
        return notificationService.sendBookingConfirmEmail(jobSeeker.getEmail(), model);
    }

    private ResponsePayload sendBookingPendingEmail(JobSeeker jobSeeker) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", jobSeeker.getFirstName().toUpperCase() + " " + jobSeeker.getLastName().toUpperCase());
        return notificationService.sendBookingPendingEmail(jobSeeker.getEmail(), model);
    }

}
