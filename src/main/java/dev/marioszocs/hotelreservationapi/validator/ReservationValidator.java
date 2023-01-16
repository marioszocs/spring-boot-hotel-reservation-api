package dev.marioszocs.hotelreservationapi.validator;

import dev.marioszocs.hotelreservationapi.entity.Reservation;
import dev.marioszocs.hotelreservationapi.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static dev.marioszocs.hotelreservationapi.constant.ErrorMessages.*;

/**
 * Reservation Validator that ensures user inputs are correctly formatted
 */
@Slf4j
public class ReservationValidator {

    private static final String VALID_DATE_FORMAT = "yyyy-MM-dd";


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
     * Validator for ids
     */
    public static void validateId(Integer id) {
        if (id == 0) {
            throw new InvalidRequestException(INVALID_HOTEL_ID);
        }
    }

    /**
     * Validator for check in and check out dates
     *
     * @param checkIn
     * @param checkOut
     */
    public static void validateDates(String checkIn, String checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new InvalidRequestException(INVALID_DATE);
        }
        if (validateDateFormat(checkIn) && validateDateFormat(checkOut)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VALID_DATE_FORMAT);
                boolean orderIsValid = simpleDateFormat.parse(checkIn).before((simpleDateFormat.parse(checkOut)));
                if (!orderIsValid)
                    throw new InvalidRequestException(INVALID_DATE_ORDER);
            } catch (ParseException e) {
                log.debug("Invalid date comparison");
            }
        } else {
            throw new InvalidRequestException(INVALID_DATE);
        }
    }

    /**
     * Validator for date format (yyyy-MM-dd|
     *
     * @param date
     * @return
     */
    private static boolean validateDateFormat(String date) {
        DateFormat sdf = new SimpleDateFormat(VALID_DATE_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            log.debug("Invalid date " + date);
            return false;
        }
        return true;
    }

    /**
     * Validator for the guest number
     *
     * @param guests
     */
    public static void validateGuest(Integer guests) {
        if (guests == null || guests <= 0) {
            throw new InvalidRequestException(INVALID_GUESTS);
        }
    }
}
