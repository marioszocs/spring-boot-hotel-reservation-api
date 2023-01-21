package dev.marioszocs.hotelreservationapi.validator;

import dev.marioszocs.hotelreservationapi.entity.Reservation;
import dev.marioszocs.hotelreservationapi.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import static dev.marioszocs.hotelreservationapi.constants.ErrorMessages.INVALID_GUESTS;

/**
 * Reservation Validator that ensures user inputs are correctly formatted
 */
@Slf4j
public class ReservationValidator extends BaseValidator {

    /**
     * Validator for the Reservation POST call
     *
     * @param reservation
     */
    public static void validateReservationPOST(Reservation reservation) {
        validateDates(reservation.getCheckIn(), reservation.getCheckOut());
        validateGuest(reservation.getGuests());
    }

    /**
     * Validator for the guest number
     *
     * @param guests
     */
    public static void validateGuest(Integer guests) {
        if (guests == null || guests <= 0) {
            log.error("Invalid guests number: '{}', guests must be a non-zero number.", guests);
            throw new InvalidRequestException(INVALID_GUESTS);
        }
    }
}
