package com.example.thejobs.dto.dashboard;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodayCountDTO {

    private String yesterday;
    private String today;
    private long yesterdayCount;
    private long todayCount;
    private long increaseToday;
}
