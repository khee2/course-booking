package com.ringle.coursebooking.application.port.out;

import com.ringle.coursebooking.domain.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingPort {
    void saveBooking(Booking booking);

    List<Booking> findBookingByStudentId(Long studentId);
}
