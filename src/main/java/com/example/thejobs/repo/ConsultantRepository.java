package com.example.thejobs.repo;

import com.example.thejobs.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<Consultant, String> {

}
