package com.example.thejobs.utility;

import com.example.thejobs.dto.enums.DAYS;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utility {

    public static List<String> generateValues(String startTime, String endTime) throws ParseException {
        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);

            List<String> timeSlots = new ArrayList<>();
            timeSlots.add(startTime);

            long currentTime = start.getTime();
            long endTimeMillis = end.getTime();

            while (currentTime < endTimeMillis - TimeUnit.MINUTES.toMillis(30)) {
                currentTime += TimeUnit.MINUTES.toMillis(30);
                timeSlots.add(sdf.format(new Date(currentTime)));
            }

            return timeSlots;
        } else {
            return null;
        }


    }

    public static DAYS getDayName(String date) {
        LocalDate localDate = LocalDate.of(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]));
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        Locale locale = Locale.getDefault();
        return DAYS.valueOf(dayOfWeek.getDisplayName(TextStyle.FULL, locale).toUpperCase());
    }
}
