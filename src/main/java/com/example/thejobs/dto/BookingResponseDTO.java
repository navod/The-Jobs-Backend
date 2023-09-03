package com.example.thejobs.dto;

import com.example.thejobs.dto.consultant.ConsultantDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {

    private ConsultantDTO consultant;
    private JobSeekerDTO jobSeeker;
    private BookingDTO booking;

}
