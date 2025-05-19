package com.ringle.coursebooking.application.service;

import com.ringle.coursebooking.adapter.in.web.dto.SearchTutorAvailableTimeResponse;
import com.ringle.coursebooking.adapter.in.web.dto.TutorAvailableTimeResponse;
import com.ringle.coursebooking.application.port.in.CreateAvailableTimeCommand;
import com.ringle.coursebooking.application.port.in.TutorAvailableTimeUseCase;
import com.ringle.coursebooking.application.port.out.TutorAvailableTimePort;
import com.ringle.coursebooking.application.port.out.TutorPort;
import com.ringle.coursebooking.domain.Tutor;
import com.ringle.coursebooking.domain.TutorAvailableTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.ringle.coursebooking.domain.TutorAvailableTime.*;
import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TutorAvailableTimeService implements TutorAvailableTimeUseCase {

    private final TutorAvailableTimePort tutorAvailableTimePort;
    private final TutorPort tutorPort;

    @Transactional
    @Override
    public TutorAvailableTimeResponse createTutorAvailableTime(CreateAvailableTimeCommand availableTimeCommand) {
        // 1. tutor_id로 tutor 찾기
        Tutor tutor = findTutor(availableTimeCommand.getTutor_id());

        // 2. startTime을 LocalDateTime으로 변환
        LocalDateTime startTime = parseStartTime(availableTimeCommand.getStartTime());

        // 3. TutorAvailableTime 객체 생성
        TutorAvailableTime availableTime = buildAvailableTime(startTime, tutor);
        tutorAvailableTimePort.saveTutorAvailableTime(availableTime);

        log.info("튜터 가능한 시간 등록 성공. 가능한 시간 ID: {}", availableTime.getId());

        return new TutorAvailableTimeResponse(availableTime.getId());
    }

    private static LocalDateTime parseStartTime(String startTime) {
        return LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private Tutor findTutor(Long tutorId) {
        return tutorPort.findTutorById(tutorId);
    }

    @Transactional
    @Override
    public TutorAvailableTimeResponse deleteTutorAvailableTime(Long tutorAvailableTimeId) {
        tutorAvailableTimePort.deleteTutorAvailableTime(tutorAvailableTimeId);

        log.info("튜터 가능한 시간 삭제 성공");

        return new TutorAvailableTimeResponse(tutorAvailableTimeId);
    }

    @Override
    public SearchTutorAvailableTimeResponse getBySlotAndDuration(String slot, String duration) {
        LocalDateTime startTime = LocalDateTime.parse(slot, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<TutorAvailableTime> slots;

        // 1. 30분 수업은 해당 슬롯만 존재하면 된다.
        if (duration.equals("30")) {
            slots = tutorAvailableTimePort.findBySlotAndDuration(startTime);
        } else {
            // 2. 60분 수업은 해당 슬롯과 30분 후 슬롯이 존재해야 한다.
            LocalDateTime secondStartTime = startTime.plusMinutes(30);
            slots = tutorAvailableTimePort.findBySlotAndDurationIsHour(startTime, secondStartTime);
        }

        log.info("튜터 가능한 시간 조회 성공. 조회된 총 시간대: {}", slots.size());

        return convertToAvailableTimeResponse(slots);

    }

    @Override
    public SearchTutorAvailableTimeResponse getByPeriodAndDuration(String startDate, String endDate, String duration) {
        LocalDateTime startTime = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(LocalTime.MIN);
        LocalDateTime endTime = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(LocalTime.MAX);

        List<TutorAvailableTime> slots;

        // 1. 30분 수업은 해당 슬롯만 존재하면 된다.
        if (duration.equals("30")) {
            slots = tutorAvailableTimePort.findByPeriodAndDuration(startTime, endTime);
        } else {
            // 2. 60분 수업은 해당 슬롯과 30분 후 슬롯이 존재해야 한다.
            slots = tutorAvailableTimePort.findByPeriodAndDurationIsHour(startTime, endTime);
        }

        log.info("튜터 가능한 시간 조회 성공. 조회된 총 시간대: {}", slots.size());

        return convertToAvailableTimeResponse(slots);
    }

    // 조회된 튜터 가능한 시간 리스트를 튜터별로 그룹화하여 변환
    private SearchTutorAvailableTimeResponse convertToAvailableTimeResponse(List<TutorAvailableTime> slots) {
        Map<Tutor, List<LocalDateTime>> grouped = slots.stream()
                .collect(groupingBy(
                        TutorAvailableTime::getTutor,
                        mapping(TutorAvailableTime::getStartTime, toList())
                ));
        List<SearchTutorAvailableTimeResponse.Tutor> list = grouped.entrySet().stream()
                .map(entry -> new SearchTutorAvailableTimeResponse.Tutor(
                        entry.getKey().getName(),
                        entry.getKey().getUniversity(),
                        entry.getKey().getMajor(),
                        entry.getValue().stream()
                                .map(time -> time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                                .toList()
                )).toList();
        return new SearchTutorAvailableTimeResponse(list);
    }

}
