package com.example.thejobs.dto.dashboard;

import com.example.thejobs.dto.BookingResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultantDashboardRespDTO {

    private TodayCountDTO todayCountDTO;
    private int pendingCount;
    private int completedCount;
    private int rejectCount;
    private int sheduledCount;
    private List<BookingResponseDTO> todayBookingDetails;

}
