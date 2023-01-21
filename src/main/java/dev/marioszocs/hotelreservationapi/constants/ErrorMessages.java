
package dev.marioszocs.hotelreservationapi.constants;

/**
 * Collection of Error Messages for Invalid Requests
 */
public final class ErrorMessages {
    public static final String INVALID_NAME = "Invalid Name: Name cannot be null.";
    public static final String INVALID_TYPE = "Invalid Type: Must be one of the following [DELUXE, LUXURY, SUITE].";
    public static final String INVALID_DATE = "Invalid date: Please input dates in 'yyyy-MM-dd' format.";
    public static final String INVALID_DATE_ORDER = "Invalid date: Start date must be before end date.";
    public static final String INVALID_RESERVATION_DATES = "Invalid Reservation Dates: The check in and/or check out dates are outside the available dates.";
    public static final String INVALID_DATE_OVERLAP = "Invalid Date: The entered date overlaps with an already registered reservation.";
    public static final String INVALID_DATE_NULL_VALUES = "Invalid Date: If a date is entered, both the 'startDate' and the 'endDate' must be provided.";
    public static final String EMPTY_HOTEL_DATES = "Invalid Hotel: A reservation can not be made as the user specified hotel does not have available dates.";
    public static final String INVALID_DATE_CHANGE_NULL = "Invalid Date Change: Hotel contains a reservation, therefore null hotel dates cannot be entered.";
    public static final String INVALID_HOTEL_ID = "Invalid Hotel ID: Hotel ID can not be null";
    public static final String INVALID_HOTEL_IN_RESERVATION = "Invalid Hotel ID: The Hotel ID does not exist";
    public static final String INVALID_ID_EXISTENCE = "Invalid ID: The id entered does not exist";
    public static final String INVALID_GUESTS = "Invalid Guests: Guests must be a non-zero number";
    public static final String PARSE_ERROR = "Internal Error: A parsing error occurred.";
    public static final String INVALID_HOTEL_DELETE = "Invalid Request: Cannot delete hotel as there are active reservations.";
    public static final String INVALID_HOTEL_UPDATE = "Invalid Hotel Update: Cannot update user specified hotel as the new dates conflict with an active reservation.";

    // Page number and size validation
    public static final String PAGE_NUMBER_CANNOT_BE_LESS_THAN_ZERO = "Page number cannot be less than zero.";
    public static final String SIZE_NUMBER_CANNOT_BE_LESS_THAN_ZERO = "Size number cannot be less than zero.";
    public static final String MAX_PAGE_SIZE_EXCEPTION = "Page size must not be greater than: " + AppConstants.MAX_PAGE_SIZE;

}
