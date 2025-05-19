package com.ringle.coursebooking.adapter.out.persistence;

import com.ringle.coursebooking.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
