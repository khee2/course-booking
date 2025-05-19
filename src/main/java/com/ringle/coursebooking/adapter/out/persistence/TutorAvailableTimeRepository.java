package com.ringle.coursebooking.adapter.out.persistence;

import com.ringle.coursebooking.domain.TutorAvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TutorAvailableTimeRepository extends JpaRepository<TutorAvailableTime, Long> {
    Optional<TutorAvailableTime> findByTutorIdAndStartTime(Long tutorId, LocalDateTime startDate);

    List<TutorAvailableTime> findAllByStartTimeAndBookingIsNull(LocalDateTime startTime);

    @Query("""
                         SELECT t1 FROM TutorAvailableTime t1
                         JOIN TutorAvailableTime t2 ON t1.tutor.id = t2.tutor.id
                         WHERE
                             t1.startTime = :firstStartTime
                             AND t2.startTime = :secondStartTime
                             AND t1.booking IS NULL
                             AND t2.booking IS NULL
            """)
    List<TutorAvailableTime> findBySlotAndDurationIsHour(
            @Param("firstStartTime") LocalDateTime startTime,
            @Param("secondStartTime") LocalDateTime secondStartTime
    );


    List<TutorAvailableTime> findAllByStartTimeBetweenAndBookingIsNull(
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    @Query(
            value = """
                SELECT t1.*
                FROM tutor_available_time t1
                JOIN tutor_available_time t2
                  ON t1.tutor_id = t2.tutor_id
                WHERE
                  t1.start_time BETWEEN :startDate AND :endDate
                  AND t2.start_time = t1.start_time + interval '30 minutes'
                  AND t1.booking_id IS NULL
                  AND t2.booking_id IS NULL
    """,
            nativeQuery = true
    )
    List<TutorAvailableTime> findByPeriodAndDurationIsHour(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
