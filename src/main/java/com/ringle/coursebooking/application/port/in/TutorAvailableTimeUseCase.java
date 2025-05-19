package com.ringle.coursebooking.application.port.in;

import com.ringle.coursebooking.adapter.in.web.dto.SearchTutorAvailableTimeResponse;
import com.ringle.coursebooking.adapter.in.web.dto.TutorAvailableTimeResponse;

public interface TutorAvailableTimeUseCase {
    // 튜터가 수업 가능한 시간대 생성 & 삭제
    TutorAvailableTimeResponse createTutorAvailableTime(CreateAvailableTimeCommand command);
    TutorAvailableTimeResponse deleteTutorAvailableTime(Long id);

    SearchTutorAvailableTimeResponse getBySlotAndDuration(String slot, String duration);

    SearchTutorAvailableTimeResponse getByPeriodAndDuration(String startDate, String endDate, String duration);
}
