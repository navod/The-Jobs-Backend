package com.example.thejobs.entity;

import com.example.thejobs.dto.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "main_user")
public class MainUser {
    @Id
    private String id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String mobile;
    @Column(nullable = false, unique = true)
    private String nic;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(columnDefinition = "boolean default 1")
    private boolean status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;
}

