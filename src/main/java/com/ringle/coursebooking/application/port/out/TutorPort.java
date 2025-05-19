package com.ringle.coursebooking.application.port.out;

import com.ringle.coursebooking.domain.Tutor;

public interface TutorPort {
    Tutor findTutorById(Long id);
}
