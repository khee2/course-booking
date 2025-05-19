package com.ringle.coursebooking.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingCommand {
    // 시간대, 수업길이, 튜터로 새로운 수업 신청
    private String startTime;
    private int durationMinutes; // 30 or 60
    private Long tutorId;
    private Long studentId;
}
