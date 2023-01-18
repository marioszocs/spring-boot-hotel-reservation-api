package dev.marioszocs.hotelreservationapi.validator;

import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.entity.ValidTypesOfHotelsEnum;
import dev.marioszocs.hotelreservationapi.exception.InvalidRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testing the HotelValidator class...
 */
class HotelValidatorTest {

    @Test
    void validateHotelPOST_ValidCase() {
        Hotel hotel = Hotel.builder()
                .name("Hilton Hotel")
                .description("5* Hotel...")
                .availableFrom("2023-01-01")
                .availableTo("2023-12-31")
                .type(ValidTypesOfHotelsEnum.DELUXE)
                .status(true)
                .build();
        Assertions.assertDoesNotThrow(() -> HotelValidator.validateHotelPOST(hotel));
    }

    @Test
    void validateHotelPOST_InvalidCase() {
        Hotel hotel = Hotel.builder()
                .name(" ")
                .description("5* Hotel...")
                .availableFrom("2023-01-01")
                .availableTo("2023-12-31")
                .type(ValidTypesOfHotelsEnum.DELUXE)
                .status(true)
                .build();
        Assertions.assertThrows(InvalidRequestException.class, () -> HotelValidator.validateHotelPOST(hotel));
    }

    @Test
    void validateHotelPATCH_ValidCase() {
        Hotel hotel = Hotel.builder()
                .name("Hilton Hotel")
                .description("5* Hotel...")
                .availableFrom("2023-01-01")
                .availableTo("2023-12-31")
                .type(ValidTypesOfHotelsEnum.DELUXE)
                .status(true)
                .build();
        Assertions.assertDoesNotThrow(() -> HotelValidator.validateHotelPOST(hotel));
    }

    @Test
    void validateId_ValidCase() {
        Integer id = 1;
        Assertions.assertDoesNotThrow(() -> HotelValidator.validateId(id));
    }

    @Test
    void validateId_InvalidCase1() {
        Integer id = null;
        Assertions.assertThrows(NullPointerException.class, () -> HotelValidator.validateId(id));
    }

    @Test
    void validateName_ValidCase() {
        String name = "Hilton Hotel";
        Assertions.assertDoesNotThrow(() -> HotelValidator.validateName(name));
    }

    @DisplayName("Invalid Hotel name testing...")
    @Test
    void validateName_InvalidCase() {
        String name = null;
        Assertions.assertThrows(InvalidRequestException.class, () -> HotelValidator.validateName(name));
    }

    @Test
    void validateDates_ValidCase1() {
        String from = "2023-01-01";
        String to = "2024-01-01";
        Assertions.assertDoesNotThrow(() -> HotelValidator.validateDates(from, to));
    }

    @Test
    void validateDates_InvalidCase1() {
        String from = "2023-01-01";
        String to = "2022-01-01";
        Assertions.assertThrows(InvalidRequestException.class, () -> HotelValidator.validateDates(from, to));
    }

    @Test
    void validateDates_InvalidCase2() {
        String from = "Hello";
        String to = "2022-01-01";
        Assertions.assertThrows(InvalidRequestException.class, () -> HotelValidator.validateDates(from, to));
    }

    @Test
    void validateDateFormat_ValidCase() {
        String validDate = "2023-01-01";
        Assertions.assertDoesNotThrow(() -> HotelValidator.validateDateFormat(validDate));
    }

    @Test
    void validateDateFormat_InvalidCase() {
        String invalidDate = "2023/01/01";
        Assertions.assertThrows(InvalidRequestException.class, () -> HotelValidator.validateDateFormat(invalidDate));
    }

}