package com.ringle.coursebooking.application.port.out;

import com.ringle.coursebooking.domain.Student;

public interface StudentPort {
    Student findStudentById(Long studentId);
}
