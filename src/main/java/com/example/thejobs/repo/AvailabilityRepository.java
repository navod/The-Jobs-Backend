package com.example.thejobs.repo;

import com.example.thejobs.dto.enums.DAYS;
import com.example.thejobs.entity.Availability;
import com.example.thejobs.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability,Integer> {


    List<Availability> findByConsultantId(String consultantId);

    Availability findAvailabilitiesByDayAndConsultant(DAYS day, Consultant consultant);


}
