package com.ringle.coursebooking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Booking {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int durationMinutes;

    @Column(nullable = false, length = 50)
    private String status; // "REQUESTED", "CONFIRMED", "CANCELLED"

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @OneToMany(mappedBy = "booking")
    private List<TutorAvailableTime> tutorAvailableTimes = new ArrayList<>();

    public static Booking saveBooking(int durationMinutes, LocalDateTime startTime, Student student, Tutor tutor) {
        Booking booking = new Booking();
        booking.durationMinutes = durationMinutes;
        booking.status = "REQUESTED";
        booking.startTime = startTime;
        booking.student = student;
        booking.tutor = tutor;

        booking.addStudent(student);
        booking.addTutor(tutor);

        return booking;
    }

    private void addStudent(Student student) {
        this.student = student;
        student.getBookings().add(this);
    }

    private void addTutor(Tutor tutor) {
        this.tutor = tutor;
        tutor.getBookings().add(this);
    }
}
