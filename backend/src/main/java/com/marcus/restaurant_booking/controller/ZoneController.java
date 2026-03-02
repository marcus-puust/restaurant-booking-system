package com.marcus.restaurant_booking.controller;

import com.marcus.restaurant_booking.model.Zone;
import com.marcus.restaurant_booking.repository.ZoneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneRepository zoneRepository;

    public ZoneController(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    // GET all zones
    @GetMapping
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    // GET zone by id
    @GetMapping("/{id}")
    public Zone getZoneById(@PathVariable Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
    }

    // POST create new zone
    @PostMapping
    public Zone createZone(@RequestBody Zone zone) {
        return zoneRepository.save(zone);
    }

    // DELETE zone
    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Long id) {
        zoneRepository.deleteById(id);
    }
}