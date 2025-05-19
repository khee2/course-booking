package com.ringle.coursebooking.common.exception;

public class InvalidStartTimeException extends BadRequestException {
    public InvalidStartTimeException(ExceptionCode code) {
        super(code);
    }

    public InvalidStartTimeException(ExceptionCode code, String message) {
        super(code, message);
    }
}
