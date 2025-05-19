package com.ringle.coursebooking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Tutor {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String university;

    @OneToMany(mappedBy = "tutor")
    private List<TutorAvailableTime> availableTimes = new ArrayList<>();

    @OneToMany(mappedBy = "tutor")
    private List<Booking> bookings = new ArrayList<>();
}
