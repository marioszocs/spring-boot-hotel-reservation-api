package dev.marioszocs.hotelreservationapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception Handler for the Hotel Reservation API
 */
@Slf4j
@ControllerAdvice
public class HotelApiExceptionHandler {

    /**
     * Exception handler for invalid requests.
     *
     * @param e InvalidRequestException
     */
    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<ApiErrorMessage> handleInvalidRequest(InvalidRequestException e) {
        return handleBadRequest(e);
    }

    private ResponseEntity<ApiErrorMessage> handleBadRequest(Exception e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e); // get full stacktrace
        return new ResponseEntity<>(new ApiErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
