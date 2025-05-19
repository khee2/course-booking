package com.ringle.coursebooking.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SearchTutorAvailableTimeResponse(@JsonProperty("LessonByTutor") List<Tutor> tutors) {

    public record Tutor(String tutorName, String university, String major, List<String> startTimes) {
    }
}
