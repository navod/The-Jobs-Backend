package com.example.thejobs.entity;

import com.example.thejobs.dto.enums.DAYS;
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
public class Availability {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DAYS day;
    private String startTime;
    private String endTime;
    private String timeSlots;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consultant_id", referencedColumnName = "id", nullable = false)
    public Consultant consultant;
}
