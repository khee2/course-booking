package com.ringle.coursebooking.application.port.out;

import com.ringle.coursebooking.domain.TutorAvailableTime;

import java.time.LocalDateTime;
import java.util.List;

public interface TutorAvailableTimePort {
    void saveTutorAvailableTime(TutorAvailableTime tutorAvailableTime);
    void deleteTutorAvailableTime(Long id);

    TutorAvailableTime findByTutorIdAndStartTime(Long tutorId, LocalDateTime startDate);

    List<TutorAvailableTime> findBySlotAndDuration(LocalDateTime startTime);


    List<TutorAvailableTime> findBySlotAndDurationIsHour(LocalDateTime startTime, LocalDateTime secondStartTime);


    List<TutorAvailableTime> findByPeriodAndDuration(LocalDateTime startDate, LocalDateTime endDate);

    List<TutorAvailableTime> findByPeriodAndDurationIsHour(LocalDateTime startDate, LocalDateTime endDate);
}
