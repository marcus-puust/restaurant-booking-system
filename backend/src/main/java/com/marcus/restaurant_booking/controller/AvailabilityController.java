package com.marcus.restaurant_booking.controller;

import com.marcus.restaurant_booking.model.Booking;
import com.marcus.restaurant_booking.model.RestaurantTable;
import com.marcus.restaurant_booking.repository.BookingRepository;
import com.marcus.restaurant_booking.repository.RestaurantTableRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final RestaurantTableRepository restaurantTableRepository;
    private final BookingRepository bookingRepository;

    public AvailabilityController(RestaurantTableRepository restaurantTableRepository,
                                  BookingRepository bookingRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Returns tables that are available in the given time range.
     *
     * Filters:
     * - start / end (time range)
     * - partySize (minimum capacity)
     * - optional zone (Window, Privacy, Kid's playground.
     *
     * A table is considered available if:
     * 1) it is active (it is not active for example table is broken)
     * 2) it has enough capacity
     * 3) it has no overlapping booking in the given time range
     */
    @GetMapping
    public List<RestaurantTable> getAvailableTables(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end,

            @RequestParam Integer partySize,
            @RequestParam(required = false) Long zoneId
    ) {

        // Step 1: Fetch candidate tables based on active status,
        // minimum capacity and optional zone filter.
        List<RestaurantTable> candidates = (zoneId == null)
                ? restaurantTableRepository
                .findByActiveTrueAndCapacityGreaterThanEqual(partySize)
                : restaurantTableRepository
                .findByActiveTrueAndCapacityGreaterThanEqualAndZone_Id(partySize, zoneId);

        // Step 2: Find bookings that overlap with the requested time range.
        // Overlap rule:
        // booking.startTime < end AND booking.endTime > start
        List<Booking> overlapping = bookingRepository
                .findOverlappingBookings(start, end);

        // Step 3: Collect IDs of tables that are already booked.
        // Using Set for fast lookup (O(1) contains check).
        Set<Long> bookedTableIds = overlapping.stream()
                .filter(b -> b.getTable() != null && b.getTable().getId() != null)
                .map(b -> b.getTable().getId())
                .collect(Collectors.toSet());

        // Step 4: Remove already booked tables from candidate list.
        return candidates.stream()
                .filter(t -> t.getId() != null
                        && !bookedTableIds.contains(t.getId()))
                .toList();
    }
}