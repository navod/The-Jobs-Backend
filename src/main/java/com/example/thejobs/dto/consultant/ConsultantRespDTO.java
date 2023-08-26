package com.example.thejobs.dto.consultant;


import com.example.thejobs.entity.Availability;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ConsultantRespDTO {
    private ConsultantDTO consultant;
    private List<Availability> availabilities;
}
