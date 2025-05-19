package com.ringle.coursebooking.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAvailableTimeCommand {
    private Long tutor_id;
    private String startTime;
}
