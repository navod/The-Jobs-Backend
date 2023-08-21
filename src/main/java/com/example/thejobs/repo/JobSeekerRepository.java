package com.example.thejobs.repo;

import com.example.thejobs.entity.Consultant;
import com.example.thejobs.entity.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, String> {
}
