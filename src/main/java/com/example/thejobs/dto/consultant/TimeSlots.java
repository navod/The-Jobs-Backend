package com.example.thejobs.dto.consultant;

import com.example.thejobs.dto.enums.DAYS;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class TimeSlots {


    @Enumerated(EnumType.STRING)
    private DAYS day;
    private String startTime;
    private String endTime;
    private Integer id;
    private boolean status;


}
