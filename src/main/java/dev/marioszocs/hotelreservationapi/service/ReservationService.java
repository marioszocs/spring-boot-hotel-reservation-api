package dev.marioszocs.hotelreservationapi.service;

import dev.marioszocs.hotelreservationapi.dto.IdEntity;
import dev.marioszocs.hotelreservationapi.dto.SuccessEntity;
import dev.marioszocs.hotelreservationapi.entity.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation getReservation(Integer id);
    IdEntity saveReservation(Reservation reservations);
    SuccessEntity deleteReservation(Integer id);
    boolean validateHotelExistenceById(Integer id);
    boolean dateIsBefore(String date1, String date2);
    boolean reservationOverlaps(Reservation reservations);
    boolean validateReservationExistence(Integer id);
}
