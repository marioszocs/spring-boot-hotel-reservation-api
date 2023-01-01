package dev.marioszocs.hotelreservationapi.validator;

import dev.marioszocs.hotelreservationapi.constant.ErrorMessages;
import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.entity.ValidTypesOfHotelsEnum;
import dev.marioszocs.hotelreservationapi.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
public class HotelValidator {

    /**
     * Validator for the Hotel POST call
     * @param hotel
     */
    public static void validateHotelPOST(Hotel hotel){
        validateName(hotel.getName());
        validateType(hotel.getType());
        validateDates(hotel.getAvailableFrom(), hotel.getAvailableTo());
    }

    /**
     * Validator for the Hotel PATCH call
     *
     * @param hotel
     */
    public static void validateHotelPATCH(Hotel hotel){
        validateId(hotel.getId());
        validateName(hotel.getName());
        validateType(hotel.getType());
        validateDates(hotel.getAvailableFrom(), hotel.getAvailableTo());
    }

    /**
     * Validator for the Hotel id
     *
     * @param id
     */
    public static void validateId(UUID id) {
        if (id.equals(null))
            throw new InvalidRequestException(ErrorMessages.INVALID_HOTEL_ID);
    }

    /**
     * Validator for the Hotel name
     *
     * @param name
     */
    public static void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidRequestException(ErrorMessages.INVALID_NAME);
        }
    }

    /**
     * Validator for available from and available to order
     *
     * @param from
     * @param to
     */
    public static void validateDates(String from, String to) {
        //Checks to see if one of the dates are null and throws an exception if only one date has been entered.
        if (StringUtils.hasText(from) != StringUtils.hasText(to)) {
            throw new InvalidRequestException(ErrorMessages.INVALID_DATE_NULL_VALUES);
        } else if (StringUtils.hasText(from) && StringUtils.hasText(to)) {
            //If the dates contain a string value then proceed.
            if (validateDateFormat(from) && validateDateFormat(to)) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    boolean orderIsValid = sdf.parse(from).before(sdf.parse(to));
                    if (!orderIsValid) {
                        throw new InvalidRequestException(ErrorMessages.INVALID_DATE_ORDER);
                    }
                } catch (ParseException e) {
                    log.debug("Invalid date comparison");
                }
            } else {
                throw new InvalidRequestException(ErrorMessages.INVALID_DATE);
            }
        } else {
            //Both dates are null, and therefore valid so no operation is performed
        }
    }

    /**
     * Validator for the date format (yyyy-MM-dd)
     *
     * @param date
     * @return
     */
    public static boolean validateDateFormat(String date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            log.debug("Invalid date " + date);
            throw new InvalidRequestException(ErrorMessages.INVALID_DATE);
        }
        return true;
    }

    /**
     * Validator for the Hotel type
     *
     * @param type
     */
    public static void validateType(ValidTypesOfHotelsEnum type) {
        if (type == null || !Arrays.asList("DELUXE", "LUXURY", "SUITE").contains(type)) {
            throw new InvalidRequestException(ErrorMessages.INVALID_TYPE);
        }
    }

}
