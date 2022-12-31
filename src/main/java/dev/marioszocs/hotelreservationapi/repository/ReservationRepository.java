package dev.marioszocs.hotelreservationapi.repository;

import dev.marioszocs.hotelreservationapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
