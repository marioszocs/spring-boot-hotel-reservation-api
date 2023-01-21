package dev.marioszocs.hotelreservationapi.exception;

import java.io.Serial;

/**
 * Invalid Request Exceptions for the Hotel Reservation API
 */
public class InvalidRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2178386706175989636L;

    public InvalidRequestException(String message) {
        super(message);
    }
}
