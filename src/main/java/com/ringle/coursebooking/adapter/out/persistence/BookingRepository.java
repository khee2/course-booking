package com.ringle.coursebooking.adapter.out.persistence;

import com.ringle.coursebooking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudentId(Long studentId);
}
