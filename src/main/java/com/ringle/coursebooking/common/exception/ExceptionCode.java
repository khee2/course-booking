package com.ringle.coursebooking.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),
    TUTOR_AVAILABLE_TIME_NOT_FOUND(1001, "유효하지 않은 튜터의 예약 가능 시간입니다."),
    TUTOR_NOT_FOUND(1002, "유효하지 않은 튜터입니다."),
    STUDENT_NOT_FOUND(1003, "유효하지 않은 학생입니다."),
    VALIDATION_ERROR(1005, "유효하지 않은 값을 입력했습니다."),
    ALREADY_BOOKED(1006, "이미 예약된 시간입니다."),
    INVALID_START_TIME(1007, "예약 시작 시간이 잘못되었습니다."),
    INTERNAL_SERVER_ERROR(9999, "서버에서 에러가 발생하였습니다.");

    private final int code;
    private final String message;
}
