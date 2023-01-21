package dev.marioszocs.hotelreservationapi.validator;

import dev.marioszocs.hotelreservationapi.constants.ErrorMessages;
import dev.marioszocs.hotelreservationapi.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static dev.marioszocs.hotelreservationapi.constants.ErrorMessages.*;

/**
 * Base Validator that ensures user inputs are correctly formatted
 */
@Slf4j
public class BaseValidator {

    private static final String VALID_DATE_FORMAT = "yyyy-MM-dd";
    private static final DateFormat simpleDateFormat = new SimpleDateFormat(VALID_DATE_FORMAT);

    /**
     * Validator for the Hotel id
     *
     * @param id
     */
    public static void validateId(Integer id) {
        if (id == 0) {
            throw new InvalidRequestException(ErrorMessages.INVALID_HOTEL_ID);
        }
    }

    /**
     * Validator for date format (yyyy-MM-dd)
     *
     * @param date
     * @return
     */
    public static boolean validateDateFormat(String date) {
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            log.error("Invalid date format: '{}', please input dates in 'yyyy-MM-dd' format.", date);
            throw new InvalidRequestException(INVALID_DATE);
            // return false;
        }
        return true;
    }

    /**
     * Validator for 'startDate' and 'endDate'
     *
     * @param startDate
     * @param endDate
     */
    public static void validateDates(String startDate, String endDate) {
        // Checks to see if one of the dates are null and throws an exception if only one date has been entered.
        if (startDate == null || endDate == null) {
            throw new InvalidRequestException(INVALID_DATE_NULL_VALUES);
        }
        if (validateDateFormat(startDate) && validateDateFormat(endDate)) {
            try {
                if (simpleDateFormat.parse(startDate).after(simpleDateFormat.parse(endDate))) {
                    log.error("Start date: '{}' mus be before end date: '{}'.", startDate, endDate);
                    throw new InvalidRequestException(INVALID_DATE_ORDER);
                }
            } catch (ParseException e) {
                log.debug("Invalid date comparison.");
            }
        } else {
            log.error("Invalid date: Please input dates in 'yyyy-MM-dd' format.");
            throw new InvalidRequestException(INVALID_DATE);
        }
    }
}
