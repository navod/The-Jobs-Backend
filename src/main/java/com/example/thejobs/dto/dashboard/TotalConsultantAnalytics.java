package com.example.thejobs.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalConsultantAnalytics {
    private String firstName;
    private String lastName;
    private String id;
    private String email;
    private String jobType;
    private String country;
    private long todayCount;
    private long rejectCount;
    private long completedCount;
    private long pendingCount;
}
