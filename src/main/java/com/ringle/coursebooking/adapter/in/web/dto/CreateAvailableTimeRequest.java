package com.ringle.coursebooking.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAvailableTimeRequest {
    @NotNull(message = "튜터 ID는 필수입니다.")
    private Long tutorId;

    @NotNull(message = "시작 시간은 필수입니다.")
    private String startTime;
}
