package com.marcus.restaurant_booking.controller;


import com.marcus.restaurant_booking.model.RestaurantTable;
import com.marcus.restaurant_booking.repository.RestaurantTableRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class RestaurantTableController {

    private final RestaurantTableRepository restaurantTableRepository;

    public RestaurantTableController(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    //GET all zones
    @GetMapping
    public List<RestaurantTable> getTables() {
        return restaurantTableRepository.findAll();
    }

    // Get table by id
    @GetMapping("/{id}")
    public RestaurantTable GetTableById(@PathVariable Long id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
    }

    //POST create new table
    @PostMapping
    public RestaurantTable createTable(@RequestBody RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    //DELETE table
    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable Long id) {
        restaurantTableRepository.deleteById(id);
    }

}
