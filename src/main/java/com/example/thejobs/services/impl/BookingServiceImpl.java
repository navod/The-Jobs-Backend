package com.example.thejobs.services.impl;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.BookingDTO;
import com.example.thejobs.dto.JobSeekerDTO;
import com.example.thejobs.entity.Booking;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.JobSeeker;
import com.example.thejobs.repo.BookingRepository;
import com.example.thejobs.repo.ConsultantRepository;
import com.example.thejobs.repo.JobSeekerRepository;
import com.example.thejobs.services.BookingService;
import com.example.thejobs.services.ConsultantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
        JobSeeker jobSeeker = modelMapper.map(jobSeekerDTO, JobSeeker.class);
        jobSeeker.setId(UUID.randomUUID().toString());
        JobSeeker saveJobSeeker = jobSeekerRepository.save(jobSeeker);
        Consultant consultant = consultantRepository.findByCountryAndJobType(jobSeeker.getPreferDestination(), jobSeekerDTO.getPreferJobType());
        if (consultant == null) {
            consultant = consultantRepository.findByCountry(consultant.getCountry());
        }

        Booking booking = Booking.builder()
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

            booking.setDate(booking.getDate());
            booking.setStatus("APPROVED");
            booking.setTime(booking.getTime());
            booking.setSpecialNote(booking.getSpecialNote());

            bookingRepository.save(booking);
        } else {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Booking id does not exists", HttpStatus.OK);
        }
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Booking Accepted", HttpStatus.OK);

    }

}
