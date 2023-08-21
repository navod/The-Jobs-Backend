package com.example.thejobs.entity;

import com.example.thejobs.dto.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_seeker")
public class JobSeeker {
    @Id
    private String id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String preferDestination;
    @Column(nullable = false)
    private String preferJobType;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String mobile;
    private String cv;
    private int age;
}
