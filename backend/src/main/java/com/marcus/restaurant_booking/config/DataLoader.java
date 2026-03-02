package com.marcus.restaurant_booking.config;

import com.marcus.restaurant_booking.model.*;
import com.marcus.restaurant_booking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final ZoneRepository zoneRepository;
    private final RestaurantTableRepository tableRepository;
    private final BookingRepository bookingRepository;

    public DataLoader(ZoneRepository zoneRepository,
                      RestaurantTableRepository tableRepository,
                      BookingRepository bookingRepository) {
        this.zoneRepository = zoneRepository;
        this.tableRepository = tableRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) {


        if (zoneRepository.count() > 0) {
            return;
        }

        // ===== ZONES =====
        Zone Window = new Zone();
        Window.setName("Akna all");

        Zone Privacy = new Zone();
        Privacy.setName("Privaatsus");

        Zone ChildPlace = new Zone();
        ChildPlace.setName("Laste mängunurk");

        zoneRepository.save(Window);
        zoneRepository.save(Privacy);
        zoneRepository.save(ChildPlace);

        // ===== TABLES =====
        RestaurantTable t1 = new RestaurantTable();
        t1.setTableNumber(1);
        t1.setCapacity(4);
        t1.setActive(true);
        t1.setZone(Window);

        RestaurantTable t2 = new RestaurantTable();
        t2.setTableNumber(2);
        t2.setCapacity(2);
        t2.setActive(true);
        t2.setZone(Privacy);

        RestaurantTable t3 = new RestaurantTable();
        t3.setTableNumber(3);
        t3.setCapacity(6);
        t3.setActive(true);
        t3.setZone(Window);

        RestaurantTable t4 = new RestaurantTable();
        t4.setTableNumber(4);
        t4.setCapacity(8);
        t4.setActive(true);
        t4.setZone(Privacy);

        RestaurantTable t5 = new RestaurantTable();
        t5.setTableNumber(5);
        t5.setCapacity(4);
        t5.setActive(false); // mitte aktiivne laud
        t5.setZone(ChildPlace);

        tableRepository.save(t1);
        tableRepository.save(t2);
        tableRepository.save(t3);
        tableRepository.save(t4);
        tableRepository.save(t5);

        // ===== BOOKINGS =====
        Booking b1 = new Booking();
        b1.setStartTime(LocalDateTime.now().plusDays(1).withHour(18).withMinute(0));
        b1.setEndTime(LocalDateTime.now().plusDays(1).withHour(20).withMinute(0));
        b1.setPartySize(2);
        b1.setStatus("CONFIRMED");
        b1.setTable(t1);

        Booking b2 = new Booking();
        b2.setStartTime(LocalDateTime.now().plusDays(1).withHour(19).withMinute(0));
        b2.setEndTime(LocalDateTime.now().plusDays(1).withHour(21).withMinute(0));
        b2.setPartySize(5);
        b2.setStatus("CONFIRMED");
        b2.setTable(t3);

        bookingRepository.save(b1);
        bookingRepository.save(b2);

        System.out.println("✅ Test data loaded successfully!");
    }
}