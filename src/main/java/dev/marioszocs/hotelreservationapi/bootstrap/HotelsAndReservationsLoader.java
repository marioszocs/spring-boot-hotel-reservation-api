package dev.marioszocs.hotelreservationapi.bootstrap;

import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.entity.Reservation;
import dev.marioszocs.hotelreservationapi.repository.HotelRepository;
import dev.marioszocs.hotelreservationapi.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class HotelsAndReservationsLoader implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {

        if (hotelRepository.count() == 0) {
            log.info("Loading data from Hotels...");
            loadHotelObject();
        }

        if (reservationRepository.count() == 0) {
            log.info("Loading data from Reservations...");
            loadReservationObject();
        }


    }

    private void loadReservationObject() {
        Reservation r1 = Reservation.builder()
                .hotelId(UUID.randomUUID())
                .checkIn("2023-01-12")
                .checkOut("2023-01-19")
                .guests(2)
                .build();

        Reservation r2 = Reservation.builder()
                .hotelId(UUID.randomUUID())
                .checkIn("2023-01-12")
                .checkOut("2023-01-19")
                .guests(2)
                .build();

        Reservation r3 = Reservation.builder()
                .hotelId(UUID.randomUUID())
                .checkIn("2023-01-12")
                .checkOut("2023-01-19")
                .guests(2)
                .build();

        Reservation r4 = Reservation.builder()
                .hotelId(UUID.randomUUID())
                .checkIn("2023-01-12")
                .checkOut("2023-01-19")
                .guests(9)
                .build();

        reservationRepository.save(r1);
        reservationRepository.save(r2);
        reservationRepository.save(r3);
        reservationRepository.save(r4);

        log.info("Loaded Reservations: " + reservationRepository.count());
    }

    private void loadHotelObject() {
        Hotel h1 = Hotel.builder()
                .name("Hotel Lux Bratislava")
                .type("LUXURY")
                .description("Luxury Hotel")
                .availableFrom("2020-10-12")
                .availableTo("2023-12-12")
                .status(true)
                .build();

        Hotel h2 = Hotel.builder()
                .name("Hotel Delux Bratislava")
                .type("DELUX")
                .description("DELUX Hotel")
                .availableFrom("2020-10-12")
                .availableTo("2023-12-12")
                .status(true)
                .build();

        Hotel h3 = Hotel.builder()
                .name("Hotel Suite Bratislava")
                .type("SUITE")
                .description("Suite Hotel")
                .availableFrom("2020-10-12")
                .availableTo("2023-12-12")
                .status(true)
                .build();

        Hotel h4 = Hotel.builder()
                .name("Hotel Other Bratislava")
                .type("LUXURY")
                .description("Other Hotel")
                .availableFrom("2020-10-12")
                .availableTo("2023-12-12")
                .status(false)
                .build();

        hotelRepository.save(h1);
        hotelRepository.save(h2);
        hotelRepository.save(h3);
        hotelRepository.save(h4);

        log.info("Loaded Hotels: " + hotelRepository.count());
    }
}
