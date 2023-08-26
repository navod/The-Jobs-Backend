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

    @Override
    @Transactional
    public ResponsePayload saveBooking(JobSeekerDTO jobSeekerDTO) {
        com.example.thejobs.entity.JobSeeker jobSeeker = modelMapper.map(jobSeekerDTO, com.example.thejobs.entity.JobSeeker.class);
        jobSeeker.setId(UUID.randomUUID().toString());
        com.example.thejobs.entity.JobSeeker saveJobSeeker = jobSeekerRepository.save(jobSeeker);
        com.example.thejobs.entity.Consultant consultant = consultantRepository.findByCountryAndJobType(jobSeeker.getPreferDestination(), jobSeekerDTO.getPreferJobType());
        if (consultant == null) {
            consultant = consultantRepository.findByCountry(consultant.getCountry());
        }

        com.example.thejobs.entity.Booking booking = com.example.thejobs.entity.Booking.builder()
                .jobSeekerId(saveJobSeeker)
                .consultantId(consultant)
                .status("PENDING")
                .build();

        bookingRepository.save(booking);
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Booking Added", HttpStatus.OK);

    }

    @Override
    public ResponsePayload acceptBooking(BookingDTO bookingDTO) {
        Optional<Booking> isBooking = bookingRepository.findById(bookingDTO.getId());

        if (isBooking.isPresent()) {
            Booking booking = isBooking.get();

            booking.setDate(bookingDTO.getDate());
            booking.setStatus("APPROVED");
            booking.setTime(bookingDTO.getTime());
            booking.setSpecialNote(bookingDTO.getSpecialNote());

            bookingRepository.save(booking);
        } else {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Booking id does not exists", HttpStatus.OK);
        }
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Booking Accepted", HttpStatus.OK);

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
    public ResponsePayload getAllBooking() {
        List<com.example.thejobs.entity.Booking> all = bookingRepository.findAll();
        List<BookingResponseDTO> bookingResponseDTO = new ArrayList<>();
        for (com.example.thejobs.entity.Booking dto : all) {
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

}
