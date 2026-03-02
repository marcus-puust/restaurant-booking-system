package com.marcus.restaurant_booking.repository;

import com.marcus.restaurant_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStartTimeGreaterThanEqualAndStartTimeLessThan(
            LocalDateTime from,
            LocalDateTime to
    );

    @Query("""
        select b from Booking b
        where b.startTime < :end
        and b.endTime > :start
    """)
    List<Booking> findOverlappingBookings(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}