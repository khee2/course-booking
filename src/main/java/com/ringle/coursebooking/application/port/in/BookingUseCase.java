package com.ringle.coursebooking.application.port.in;

import com.ringle.coursebooking.adapter.in.web.dto.CreateBookingResponse;
import com.ringle.coursebooking.adapter.in.web.dto.GetMyBookingResponse;

public interface BookingUseCase {
    CreateBookingResponse createBooking(BookingCommand bookingCommand);
    GetMyBookingResponse getMyBooking(Long studentId);
}
