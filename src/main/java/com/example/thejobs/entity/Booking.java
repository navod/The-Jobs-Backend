package com.example.thejobs.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_seeker_id", referencedColumnName = "id", nullable = false)
    private JobSeeker jobSeekerId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consultant_id", referencedColumnName = "id", nullable = false)
    private Consultant consultantId;

    private String rejectReason;
    private String specialNote;

    private String status;
    private String date;
    private String time;



}
