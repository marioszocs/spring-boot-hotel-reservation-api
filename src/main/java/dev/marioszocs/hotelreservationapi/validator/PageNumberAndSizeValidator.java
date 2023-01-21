package dev.marioszocs.hotelreservationapi.validator;

import dev.marioszocs.hotelreservationapi.constants.AppConstants;
import dev.marioszocs.hotelreservationapi.exception.InvalidRequestException;

import static dev.marioszocs.hotelreservationapi.constants.ErrorMessages.*;

public class PageNumberAndSizeValidator {

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new InvalidRequestException(PAGE_NUMBER_CANNOT_BE_LESS_THAN_ZERO);
        }

        if (size < 0) {
            throw new InvalidRequestException(SIZE_NUMBER_CANNOT_BE_LESS_THAN_ZERO);
        }

        if (size > Integer.parseInt(AppConstants.MAX_PAGE_SIZE)) {
            throw new InvalidRequestException(MAX_PAGE_SIZE_EXCEPTION);
        }
    }
}
