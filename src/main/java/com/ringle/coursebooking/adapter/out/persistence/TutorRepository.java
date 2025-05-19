package com.ringle.coursebooking.adapter.out.persistence;

import com.ringle.coursebooking.domain.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
}
