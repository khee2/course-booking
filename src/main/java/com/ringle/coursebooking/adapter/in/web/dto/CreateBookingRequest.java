package com.ringle.coursebooking.adapter.in.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBookingRequest {
    // 시간대, 수업길이, 튜터로 새로운 수업 신청
    @NotNull(message = "시작 시간은 필수입니다.")
    private String startTime;

    @Min(value = 30, message = "수업 길이는 30분 또는 60분이어야 합니다.")
    @Max(value = 60, message = "수업 길이는 30분 또는 60분이어야 합니다.")
    private int durationMinutes; // 30 or 60


    @NotNull(message = "튜터 ID는 필수입니다.")
    private Long tutorId;

    @NotNull(message = "학생 ID는 필수입니다.")
    private Long studentId;
}
