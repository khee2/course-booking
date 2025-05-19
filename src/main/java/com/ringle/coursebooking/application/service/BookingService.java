package com.ringle.coursebooking.application.service;

import com.ringle.coursebooking.adapter.in.web.dto.CreateBookingResponse;
import com.ringle.coursebooking.adapter.in.web.dto.GetMyBookingResponse;
import com.ringle.coursebooking.application.port.in.BookingCommand;
import com.ringle.coursebooking.application.port.in.BookingUseCase;

import com.ringle.coursebooking.application.port.out.BookingPort;
import com.ringle.coursebooking.application.port.out.StudentPort;
import com.ringle.coursebooking.application.port.out.TutorAvailableTimePort;
import com.ringle.coursebooking.application.port.out.TutorPort;
import com.ringle.coursebooking.common.exception.AlreadyBookedException;
import com.ringle.coursebooking.common.exception.InvalidStartTimeException;
import com.ringle.coursebooking.domain.Booking;
import com.ringle.coursebooking.domain.Student;
import com.ringle.coursebooking.domain.Tutor;
import com.ringle.coursebooking.domain.TutorAvailableTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.ringle.coursebooking.adapter.in.web.dto.GetMyBookingResponse.*;
import static com.ringle.coursebooking.common.exception.ExceptionCode.*;
import static com.ringle.coursebooking.domain.Booking.*;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService implements BookingUseCase {

    private final StudentPort studentPort;
    private final TutorPort tutorPort;
    private final BookingPort bookingPort;
    private final TutorAvailableTimePort tutorAvailableTimePort;

    @Override
    @Transactional
    public CreateBookingResponse createBooking(BookingCommand bookingCommand) {
        // 1. student_id가 유효한지 판단
        Student studentById = findStudent(bookingCommand.getStudentId());

        // 2. tutor_id가 유효한지 판단
        Tutor tutorById = findTutor(bookingCommand.getTutorId());


        // 3. 필요한 시간 슬롯 가져오기
        LocalDateTime startTime = parseStartTime(bookingCommand.getStartTime());

        List<TutorAvailableTime> tutorAvailableTimes = new ArrayList<>();

        // 3.1 첫 슬롯 확인
        TutorAvailableTime firstSlot = tutorAvailableTimePort.findByTutorIdAndStartTime(
                bookingCommand.getTutorId(),
                startTime
        );

        if (firstSlot.getBooking() != null) {
            throw new AlreadyBookedException(ALREADY_BOOKED, startTime + " 시간은 이미 예약된 시간입니다.");
        }

        tutorAvailableTimes.add(firstSlot);

        // 3.2 두 번째 슬롯 확인
        TutorAvailableTime secondTime;
        if (bookingCommand.getDurationMinutes() == 60) {
            LocalDateTime secondStartTime = startTime.plusMinutes(30);
            TutorAvailableTime secondSlot = tutorAvailableTimePort.findByTutorIdAndStartTime(
                    bookingCommand.getTutorId(),
                    secondStartTime
            );

            if (firstSlot.getBooking() != null) {
                throw new AlreadyBookedException(ALREADY_BOOKED, secondStartTime + " 시간은 이미 예약된 시간입니다.");
            }

            tutorAvailableTimes.add(secondSlot);
        }

        // 4. booking 생성 (연관관계 편의 메서드 사용)
        Booking booking = saveBooking(
                bookingCommand.getDurationMinutes(),
                startTime,
                studentById,
                tutorById
        );

        // 5. booking과 tutor_available_time 관계 설정
        for (TutorAvailableTime slot : tutorAvailableTimes) {
            slot.assignBooking(booking);
        }

        bookingPort.saveBooking(booking);

        log.info("수업 신청 성공. 수업 ID: {}", booking.getId());

        return new CreateBookingResponse(booking.getId());
    }

    private static LocalDateTime parseStartTime(String startTimeString) {
        LocalDateTime parsed = LocalDateTime.parse(startTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        int minute = parsed.getMinute();
        if (minute != 0 && minute != 30) {
            throw new InvalidStartTimeException(INVALID_START_TIME, "수업 시작 시간은 정각 또는 30이어야 합니다. 입력된 시간" + startTimeString);
        }
        return parsed;
    }

    private Tutor findTutor(Long tutorId) {
        return tutorPort.findTutorById(tutorId);
    }

    private Student findStudent(Long studentId) {
        return studentPort.findStudentById(studentId);
    }

    @Override
    public GetMyBookingResponse getMyBooking(Long studentId) {
        // 튜터이름, 수업 시작 시간, 총 몇분 수업, 예약상태, 예약시간

        List<MyBooking> myBookings = bookingPort.findBookingByStudentId(studentId).stream()
                .map(booking -> new MyBooking(
                        booking.getTutor().getName(),
                        booking.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        booking.getDurationMinutes(),
                        booking.getStatus(),
                        booking.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))
                .toList();

        log.info("내가 신청한 수업 조회 성공. 조회된 내 수업의 수: {}", myBookings.size());

        return new GetMyBookingResponse(myBookings);
    }
}
