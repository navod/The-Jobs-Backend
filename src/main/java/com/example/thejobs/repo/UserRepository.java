package com.example.thejobs.repo;

import com.example.thejobs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = """
                select * from _user where email=?1 and status=1
            """, nativeQuery = true)
    Optional<User> findByEmail(String email);
}
