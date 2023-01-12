package dev.marioszocs.hotelreservationapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Model for the Error Messages
 */
@Data
@AllArgsConstructor
public class ApiErrorMessage {
    private HttpStatus status; // The status property holds the operation call status
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp; // The timestamp property holds the date-time instance when the error happened.
    private String message; // The message property holds a user-friendly message about the error.

    private ApiErrorMessage() {
        timestamp = LocalDateTime.now();
    }

    ApiErrorMessage(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiErrorMessage(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
}
