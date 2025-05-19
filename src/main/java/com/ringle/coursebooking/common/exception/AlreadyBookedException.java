package com.ringle.coursebooking.common.exception;

public class AlreadyBookedException extends BadRequestException {
    public AlreadyBookedException(ExceptionCode code) {
        super(code);
    }

    public AlreadyBookedException(ExceptionCode code, String message) {
        super(code, message);
    }
}
