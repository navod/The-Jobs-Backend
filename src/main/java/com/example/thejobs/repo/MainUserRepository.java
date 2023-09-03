package com.example.thejobs.repo;

import com.example.thejobs.entity.MainUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainUserRepository extends JpaRepository<MainUser, String> {
}
