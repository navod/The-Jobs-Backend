package com.example.thejobs.repo;

import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConsultantRepository extends JpaRepository<Consultant, String> {

    Consultant findByEmail(String email);

    Consultant findByCountryAndJobType(String country, String jobType);

    Consultant findByCountry(String country);

}
