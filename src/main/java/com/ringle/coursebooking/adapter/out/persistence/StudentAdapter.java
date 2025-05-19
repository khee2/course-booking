package com.ringle.coursebooking.adapter.out.persistence;

import com.ringle.coursebooking.application.port.out.StudentPort;
import com.ringle.coursebooking.common.PersistenceAdapter;
import com.ringle.coursebooking.common.exception.EntityNotFoundException;
import com.ringle.coursebooking.domain.Student;
import lombok.RequiredArgsConstructor;

import static com.ringle.coursebooking.common.exception.ExceptionCode.STUDENT_NOT_FOUND;

@PersistenceAdapter
@RequiredArgsConstructor
public class StudentAdapter implements StudentPort {
    private final StudentRepository studentRepository;

    @Override
    public Student findStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(STUDENT_NOT_FOUND, "ID가 " + studentId + "인 학생을 찾을 수 없습니다."));
    }
}
