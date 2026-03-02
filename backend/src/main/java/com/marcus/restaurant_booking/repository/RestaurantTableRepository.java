package com.marcus.restaurant_booking.repository;

import com.marcus.restaurant_booking.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

    List<RestaurantTable> findByActiveTrueAndCapacityGreaterThanEqual(Integer partySize);

    List<RestaurantTable> findByActiveTrueAndCapacityGreaterThanEqualAndZone_Id(Integer partySize, Long zoneId);
}