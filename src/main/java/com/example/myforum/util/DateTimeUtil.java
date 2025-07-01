package com.example.myforum.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeUtil {
    // Hiển thị thời gian dạng "x phút trước", "2 ngày trước"
    public static String timeAgo(LocalDateTime time) {
        if (time == null) return "";
        Duration duration = Duration.between(time, LocalDateTime.now());
        if (duration.toMinutes() < 1) return "Vừa xong";
        if (duration.toHours() < 1) return duration.toMinutes() + " phút trước";
        if (duration.toDays() < 1) return duration.toHours() + " giờ trước";
        return duration.toDays() + " ngày trước";
    }
}
