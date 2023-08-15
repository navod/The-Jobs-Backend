package com.example.thejobs.repo;

import com.example.thejobs.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability,Integer> {


    List<Availability> findByConsultantId(String consultant_id);


}
