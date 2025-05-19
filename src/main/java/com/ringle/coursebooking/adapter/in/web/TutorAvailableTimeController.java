package com.ringle.coursebooking.adapter.in.web;

import com.ringle.coursebooking.adapter.in.web.dto.CreateAvailableTimeRequest;
import com.ringle.coursebooking.adapter.in.web.dto.SearchTutorAvailableTimeResponse;
import com.ringle.coursebooking.adapter.in.web.dto.TutorAvailableTimeResponse;
import com.ringle.coursebooking.application.port.in.CreateAvailableTimeCommand;
import com.ringle.coursebooking.application.port.in.TutorAvailableTimeUseCase;
import com.ringle.coursebooking.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ringle.coursebooking.common.response.ApiResponse.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tutor-available-time")
public class TutorAvailableTimeController {
    private final TutorAvailableTimeUseCase tutorAvailableTimeUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<TutorAvailableTimeResponse>> createTutorAvailableTime(@RequestBody @Valid CreateAvailableTimeRequest request) {
        TutorAvailableTimeResponse response = tutorAvailableTimeUseCase.createTutorAvailableTime(
                new CreateAvailableTimeCommand(
                        request.getTutorId(),
                        request.getStartTime()
                )
        );
        return ResponseEntity.ok(success(response, "튜터 가능한 시간대 추가 성공"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<TutorAvailableTimeResponse>> deleteTutorAvailableTime(Long tutorAvailableTimeId) {
        TutorAvailableTimeResponse response = tutorAvailableTimeUseCase.deleteTutorAvailableTime(tutorAvailableTimeId);
        return ResponseEntity.ok(success(response, "튜터 가능한 시간대 삭제 성공"));
    }

    @GetMapping("/period")
    public ResponseEntity<ApiResponse<SearchTutorAvailableTimeResponse>> getByPeriodAndDuration(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "duration") String duration) {
        SearchTutorAvailableTimeResponse response = tutorAvailableTimeUseCase.getByPeriodAndDuration(
                startDate,
                endDate,
                duration
        );
        return ResponseEntity.ok(success(response, "튜터 가능한 시간대 조회 성공"));
    }

    @GetMapping("/slot")
    public ResponseEntity<ApiResponse<SearchTutorAvailableTimeResponse>> getBySlotAndDuration(
            @RequestParam(value = "slot") String slot,
            @RequestParam(value = "duration") String duration) {
        SearchTutorAvailableTimeResponse response = tutorAvailableTimeUseCase.getBySlotAndDuration(
                slot,
                duration
        );
        return ResponseEntity.ok(success(response, "튜터 가능한 시간대 조회 성공"));
    }
}
