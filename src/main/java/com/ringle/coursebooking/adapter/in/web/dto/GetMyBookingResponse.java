package com.ringle.coursebooking.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GetMyBookingResponse(@JsonProperty("myBookings") List<MyBooking> myBookings) {

    public record MyBooking(String tutorName, String startTime, int totalMinutes,
                            String bookingStatus, String bookingTime) {
    }
}
