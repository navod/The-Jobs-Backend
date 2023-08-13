package com.example.thejobs.repo;

import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultantRepository extends JpaRepository<Consultant, String> {

    Consultant findByEmail(String email);

}
