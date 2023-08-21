package com.example.thejobs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Integer id;
    private String rejectReason;
    private String specialNote;
    private String status;
    private String date;
    private String time;
}
