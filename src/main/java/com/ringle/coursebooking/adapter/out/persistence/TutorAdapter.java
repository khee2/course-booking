package com.ringle.coursebooking.adapter.out.persistence;

import com.ringle.coursebooking.application.port.out.TutorPort;
import com.ringle.coursebooking.common.PersistenceAdapter;
import com.ringle.coursebooking.common.exception.EntityNotFoundException;
import com.ringle.coursebooking.domain.Tutor;
import lombok.RequiredArgsConstructor;

import static com.ringle.coursebooking.common.exception.ExceptionCode.*;

@PersistenceAdapter
@RequiredArgsConstructor
public class TutorAdapter implements TutorPort {

    private final TutorRepository tutorRepository;
    @Override
    public Tutor findTutorById(Long tutorId) {
        return tutorRepository.findById(tutorId)
                .orElseThrow(() -> new EntityNotFoundException(TUTOR_NOT_FOUND, "ID가 " + tutorId + "인 튜터를 찾을 수 없습니다."));
    }
}
