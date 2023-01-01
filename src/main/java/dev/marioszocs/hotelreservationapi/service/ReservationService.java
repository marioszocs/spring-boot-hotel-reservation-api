package dev.marioszocs.hotelreservationapi.service;

import dev.marioszocs.hotelreservationapi.dto.IdEntity;
import dev.marioszocs.hotelreservationapi.dto.SuccessEntity;
import dev.marioszocs.hotelreservationapi.entity.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation getReservation(UUID id);
    IdEntity saveReservation(Reservation reservations);
    SuccessEntity deleteReservation(UUID id);
    boolean validateInventoryExistence(UUID id);
    boolean dateIsBefore(String date1, String date2);
    boolean reservationOverlaps(Reservation reservations);
    boolean validateReservationExistence(UUID id);
}
