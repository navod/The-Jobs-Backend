package com.example.thejobs.repo;

import com.example.thejobs.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability,Integer> {
}
