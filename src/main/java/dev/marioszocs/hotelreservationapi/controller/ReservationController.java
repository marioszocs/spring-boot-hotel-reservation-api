package dev.marioszocs.hotelreservationapi.controller;

import dev.marioszocs.hotelreservationapi.dto.IdEntity;
import dev.marioszocs.hotelreservationapi.dto.SuccessEntity;
import dev.marioszocs.hotelreservationapi.entity.Reservation;
import dev.marioszocs.hotelreservationapi.service.ReservationService;
import dev.marioszocs.hotelreservationapi.validator.ReservationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Reservation Controller containing endpoints of Reservation related API calls
 */
@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    /**
     * End point to get all reservations.
     *
     * @return list of Reservations
     */
    @GetMapping(value = "/reservations", produces = "application/json")
    public List<Reservation> getReservationList(){
        return reservationService.getAllReservations();
    }

    /**
     * End point to get user specified reservation.
     *
     * @param id Integer
     * @return Reservation object
     */
    @GetMapping(value = "/reservation/{id}", produces = "application/json")
    public Reservation getReservation(@PathVariable UUID id){
        ReservationValidator.validateId(id);
        return reservationService.getReservation(id);
    }

    /**
     * End point to update user specified Reservation
     *
     * @param reservation
     * @return
     */
    @PostMapping(value = "/reservation", produces = "application/json")
    public IdEntity saveReservation(@RequestBody Reservation reservation){
        ReservationValidator.validateReservationPOST(reservation);
        return reservationService.saveReservation(reservation);
    }

    /**
     * End point to delete user specified Reservation.
     *
     * @param id Integer
     * @return successEntity
     */
    @DeleteMapping(value = "/reservation/{id}", produces = "application/json")
    public SuccessEntity deleteReservation(@PathVariable UUID id){
        ReservationValidator.validateId(id);
        return reservationService.deleteReservation(id);
    }
}
