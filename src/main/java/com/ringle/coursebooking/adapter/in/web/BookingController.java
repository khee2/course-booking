package com.ringle.coursebooking.adapter.in.web;

import com.ringle.coursebooking.adapter.in.web.dto.CreateBookingRequest;
import com.ringle.coursebooking.adapter.in.web.dto.CreateBookingResponse;
import com.ringle.coursebooking.adapter.in.web.dto.GetMyBookingResponse;
import com.ringle.coursebooking.application.port.in.BookingCommand;
import com.ringle.coursebooking.application.port.in.BookingUseCase;
import com.ringle.coursebooking.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ringle.coursebooking.common.response.ApiResponse.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingUseCase bookingUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateBookingResponse>> createBooking(@RequestBody @Valid CreateBookingRequest createBookingRequest) {
        CreateBookingResponse response = bookingUseCase.createBooking(
                new BookingCommand(
                        createBookingRequest.getStartTime(),
                        createBookingRequest.getDurationMinutes(),
                        createBookingRequest.getTutorId(),
                        createBookingRequest.getStudentId()
                )
        );
        return ResponseEntity.ok(success(response, "수업 신청 성공"));
    }

    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<GetMyBookingResponse>> getMyBooking(Long studentId) {
        GetMyBookingResponse response = bookingUseCase.getMyBooking(studentId);
        return ResponseEntity.ok(success(response, "내가 신청한 수업 조회 성공"));
    }
}
