package com.example.thejobs.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainDashboardRespDTO {

    private TodayCountDTO todayCountDTO;
    private int pendingCount;
    private int completedCount;
    private int rejectCount;
    private int totalConsultants;
    private int activeConsultants;
    private int deactiveConsultants;
    private List <TotalConsultantAnalytics> totalConsultantsAnalytics;

}
