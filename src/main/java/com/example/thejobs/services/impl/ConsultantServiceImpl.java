package com.example.thejobs.services.impl;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.auth.RegisterRequest;
import com.example.thejobs.dto.consultant.ConsultantDTO;
import com.example.thejobs.dto.consultant.ConsultantRequestDTO;
import com.example.thejobs.dto.consultant.TimeSlots;
import com.example.thejobs.entity.Availability;
import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.User;
import com.example.thejobs.repo.AvailabilityRepository;
import com.example.thejobs.repo.ConsultantRepository;
import com.example.thejobs.repo.UserRepository;
import com.example.thejobs.services.AuthenticationService;
import com.example.thejobs.services.ConsultantService;
import com.example.thejobs.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultantServiceImpl implements ConsultantService {

    private final ConsultantRepository consultantRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;
    private final AvailabilityRepository availabilityRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponsePayload registerConsultant(ConsultantDTO consultantDTO) {
        log.info("consultant register impl-method called {} name", consultantDTO.getFirstName());
        try {
            Consultant consultant = modelMapper.map(consultantDTO, Consultant.class);
            String userId = UUID.randomUUID().toString();
            consultant.setId(userId);
            consultant.setStatus(true);
            RegisterRequest register = modelMapper.map(consultantDTO, RegisterRequest.class);
            register.setId(userId);
            ResponsePayload regRes = authenticationService.register(register);

            if (regRes.getStatus() == HttpStatus.OK) {
                log.info("saved consultant {} in user table", consultantDTO.getFirstName());
                Consultant cons = consultantRepository.save(consultant);

                List<Availability> timeSlotsDTO = new ArrayList<>();


                for (TimeSlots slot : consultantDTO.getTimeSlots()) {
                    Availability av = Availability.builder()
                            .day(slot.getDay())
                            .endTime((slot.getEndTime()))
                            .startTime(slot.getStartTime())
                            .timeSlots(objectMapper.writeValueAsString(Utility.generateValues(slot.getStartTime(), slot.getEndTime())))
                            .consultant(cons)
                            .build();

                    timeSlotsDTO.add(av);
                }
                availabilityRepository.saveAll(timeSlotsDTO);
            } else {
                log.info(regRes.getMessage());
                return new ResponsePayload(regRes.getStatus().getReasonPhrase(), regRes.getMessage(), regRes.getStatus());
            }


            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "consultant added", HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Error in register consultant method");
            return new ResponsePayload(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponsePayload updateConsultant(ConsultantDTO consultantDTO) {
        log.info("consultant update impl-method called {} name", consultantDTO.getFirstName());
        try {

            Optional<Consultant> existConsultant = consultantRepository.findById(consultantDTO.getId());
            Optional<User> existUser = userRepository.findById(consultantDTO.getId());

            if (existConsultant.isPresent() && existUser.isPresent()) {
                Consultant consultant = modelMapper.map(consultantDTO, Consultant.class);
                consultant.setStatus(true);
                consultantRepository.save(consultant);

                List<Availability> timeSlotsDTO = new ArrayList<>();


                for (TimeSlots slot : consultantDTO.getTimeSlots()) {
                    Availability av = Availability.builder()
                            .id(slot.getId())
                            .day(slot.getDay())
                            .endTime((slot.getEndTime()))
                            .startTime(slot.getStartTime())
                            .timeSlots(objectMapper.writeValueAsString(Utility.generateValues(slot.getStartTime(), slot.getEndTime())))
                            .status(slot.isStatus())
                            .consultant(consultant)
                            .build();

                    timeSlotsDTO.add(av);
                }
                availabilityRepository.saveAll(timeSlotsDTO);

            }

            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "consultant updated", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error in update consultant method");
            return new ResponsePayload(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponsePayload getAllConsultants() {
        List<ConsultantRequestDTO> consultantRequestDTOS = new ArrayList<>();
        List<Consultant> all = consultantRepository.findAll();

        for (Consultant dto : all) {
            ConsultantDTO consultantDTO = modelMapper.map(dto, ConsultantDTO.class);
            List<Availability> byConsultantId = availabilityRepository.findByConsultantId(dto.getId());
            consultantRequestDTOS.add(new ConsultantRequestDTO(consultantDTO, byConsultantId));
        }
        return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), consultantRequestDTOS, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponsePayload deactivateConsultant(String id, boolean status) {
        log.error("User consultant-impl activation method {}", id);
        Optional<Consultant> consultants = consultantRepository.findById(id);
        Optional<User> users = userRepository.findById(id);
        if (consultants.isPresent() && users.isPresent()) {
            Consultant consultant = consultants.get();
            consultant.setStatus(status);
            consultantRepository.save(consultant);

            User user = users.get();
            user.setStatus(status);
            userRepository.save(user);
            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "user successfully updated", HttpStatus.OK);
        } else {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "user does not exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponsePayload deleteConsultant(String id) {
        Optional<Consultant> consultants = consultantRepository.findById(id);
        Optional<User> users = userRepository.findById(id);

        if (consultants.isPresent() && users.isPresent()) {
            Consultant consultant = consultants.get();
            User user = users.get();

            consultantRepository.delete(consultant);
            userRepository.delete(user);

            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "user successfully deleted", HttpStatus.OK);
        } else {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "user does not exists", HttpStatus.BAD_REQUEST);
        }
    }
}
