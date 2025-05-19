package com.ringle.coursebooking.adapter.out.persistence;

import com.ringle.coursebooking.application.port.out.TutorAvailableTimePort;
import com.ringle.coursebooking.common.PersistenceAdapter;
import com.ringle.coursebooking.common.exception.EntityNotFoundException;
import com.ringle.coursebooking.domain.TutorAvailableTime;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.ringle.coursebooking.common.exception.ExceptionCode.TUTOR_AVAILABLE_TIME_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class TutorAvailableTimeAdapter implements TutorAvailableTimePort {
    private final TutorAvailableTimeRepository tutorAvailableTimeRepository;

    @Override
    public void saveTutorAvailableTime(TutorAvailableTime tutorAvailableTime) {
        tutorAvailableTimeRepository.save(tutorAvailableTime);

    }

    @Override
    public void deleteTutorAvailableTime(Long tutorAvailableTimeId) {
        tutorAvailableTimeRepository.deleteById(tutorAvailableTimeId);
    }

    @Override
    public TutorAvailableTime findByTutorIdAndStartTime(Long tutorId, LocalDateTime startDate) {
        return tutorAvailableTimeRepository.findByTutorIdAndStartTime(tutorId, startDate)
                .orElseThrow(() -> new EntityNotFoundException(TUTOR_AVAILABLE_TIME_NOT_FOUND, "ID가 " + tutorId + "인 튜터의 사용 가능한 시간이 없습니다."));
    }

    @Override
    public List<TutorAvailableTime> findBySlotAndDuration(LocalDateTime startTime) {
        return tutorAvailableTimeRepository.findAllByStartTimeAndBookingIsNull(startTime);
    }

    @Override
    public List<TutorAvailableTime> findBySlotAndDurationIsHour(LocalDateTime startTime, LocalDateTime secondStartTime) {
        return tutorAvailableTimeRepository.findBySlotAndDurationIsHour(startTime, secondStartTime);
    }

    @Override
    public List<TutorAvailableTime> findByPeriodAndDuration(LocalDateTime startDate, LocalDateTime endDate) {
        return tutorAvailableTimeRepository.findAllByStartTimeBetweenAndBookingIsNull(startDate, endDate);
    }

    @Override
    public List<TutorAvailableTime> findByPeriodAndDurationIsHour(LocalDateTime startDate, LocalDateTime endDate) {
        return tutorAvailableTimeRepository.findByPeriodAndDurationIsHour(startDate, endDate);

    }
}
