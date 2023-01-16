package dev.marioszocs.hotelreservationapi.bootstrap;

import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.entity.Reservation;
import dev.marioszocs.hotelreservationapi.entity.ValidTypesOfHotelsEnum;
import dev.marioszocs.hotelreservationapi.repository.HotelRepository;
import dev.marioszocs.hotelreservationapi.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
                .hotelId(1)
                .checkIn("2019-01-01")
                .checkOut("2019-12-31")
                .guests(2)
                .build();

        Reservation r2 = Reservation.builder()
                .hotelId(2)
                .checkIn("2020-01-01")
                .checkOut("2020-12-31")
                .guests(3)
                .build();

        Reservation r3 = Reservation.builder()
                .hotelId(3)
                .checkIn("2021-01-01")
                .checkOut("2021-12-31")
                .guests(4)
                .build();

        Reservation r4 = Reservation.builder()
                .hotelId(4)
                .checkIn("2022-01-01")
                .checkOut("2022-12-31")
                .guests(5)
                .build();

        Reservation r5 = Reservation.builder()
                .hotelId(5)
                .checkIn("2023-01-01")
                .checkOut("2023-12-31")
                .guests(6)
                .build();

        reservationRepository.save(r1);
        reservationRepository.save(r2);
        reservationRepository.save(r3);
        reservationRepository.save(r4);
        reservationRepository.save(r5);

        log.info("Loaded Reservations: " + reservationRepository.count());
    }

    private void loadHotelObject() {
        Hotel h1 = Hotel.builder()
                .name("Falkensteiner Hotel Bratislava")
                .type(ValidTypesOfHotelsEnum.SUITE)
                .description("Falkensteiner")
                .availableFrom("2019-01-01")
                .availableTo("2019-12-31")
                .status(true)
                .build();

        Hotel h2 = Hotel.builder()
                .name("Park Inn by Radisson Danube Bratislava")
                .type(ValidTypesOfHotelsEnum.SUITE)
                .description("Park Inn")
                .availableFrom("2020-01-01")
                .availableTo("2020-12-31")
                .status(true)
                .build();

        Hotel h3 = Hotel.builder()
                .name("Radisson Blu Carlton Hotel Bratislava")
                .type(ValidTypesOfHotelsEnum.DELUXE)
                .description("Radisson Blu Carlton")
                .availableFrom("2021-01-01")
                .availableTo("2021-12-31")
                .status(true)
                .build();

        Hotel h4 = Hotel.builder()
                .name("Boutique Hotel Bratislava")
                .type(ValidTypesOfHotelsEnum.SUITE)
                .description("Boutique Hotel")
                .availableFrom("2022-01-01")
                .availableTo("2022-12-31")
                .status(true)
                .build();

        Hotel h5 = Hotel.builder()
                .name("Sheraton Bratislava Hotel")
                .type(ValidTypesOfHotelsEnum.LUXURY)
                .description("Sheraton")
                .availableFrom("2023-01-01")
                .availableTo("2023-12-31")
                .status(true)
                .build();

        hotelRepository.save(h1);
        hotelRepository.save(h2);
        hotelRepository.save(h3);
        hotelRepository.save(h4);
        hotelRepository.save(h5);

        log.info("Loaded Hotels: " + hotelRepository.count());
    }
}
