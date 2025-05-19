package com.ringle.coursebooking.common.exception;

public class EntityNotFoundException extends BadRequestException {
    public EntityNotFoundException(ExceptionCode code) {
        super(code);
    }

    public EntityNotFoundException(ExceptionCode code, String message) {
        super(code, message);
    }
}
