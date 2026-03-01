package com.marcus.restaurant_booking.repository;

import com.marcus.restaurant_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
