package com.ringle.coursebooking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TutorAvailableTime {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = true)
    private Booking booking;

    public static TutorAvailableTime buildAvailableTime(LocalDateTime startTime, Tutor tutor) {
        TutorAvailableTime tutorAvailableTime = new TutorAvailableTime();
        tutorAvailableTime.startTime = startTime;
        tutorAvailableTime.addTutor(tutor);
        return tutorAvailableTime;
    }

    private void addTutor(Tutor tutor) {
        this.tutor = tutor;
        if (!tutor.getAvailableTimes().contains(this)) {
            tutor.getAvailableTimes().add(this);
        }
    }

    public void assignBooking(Booking booking) {
        this.booking = booking;
        if (!booking.getTutorAvailableTimes().contains(this)) {
            booking.getTutorAvailableTimes().add(this);
        }
    }
}
