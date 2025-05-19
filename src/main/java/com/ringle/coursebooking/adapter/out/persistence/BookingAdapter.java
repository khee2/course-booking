package com.ringle.coursebooking.adapter.out.persistence;

import com.ringle.coursebooking.application.port.out.BookingPort;
import com.ringle.coursebooking.common.PersistenceAdapter;
import com.ringle.coursebooking.domain.Booking;
import lombok.RequiredArgsConstructor;

import java.util.List;


@PersistenceAdapter
@RequiredArgsConstructor
public class BookingAdapter implements BookingPort {
    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> findBookingByStudentId(Long studentId) {
        return bookingRepository.findByStudentId(studentId);
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }
}
