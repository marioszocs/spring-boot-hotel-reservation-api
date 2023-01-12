package dev.marioszocs.hotelreservationapi.serviceImp;

import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.entity.ValidTypesOfHotelsEnum;
import dev.marioszocs.hotelreservationapi.repository.HotelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HotelServiceImpMockitoTest {
    @Mock
    HotelRepository hotelRepository;

    @InjectMocks
    HotelServiceImp hotelServiceImp;

    static List<Hotel> hotelList = new ArrayList<>();

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    public static void loadHotelList() {
        Hotel hotel = Hotel.builder()
                .name("Hilton Hotel")
                .description("5* Hotel...")
                .availableFrom("2023-01-01")
                .availableTo("2023-12-31")
                .type(ValidTypesOfHotelsEnum.DELUXE)
                .status(true)
                .build();

        Hotel hotel2 = Hotel.builder()
                .name("Four Season Hotel")
                .description("5* Hotel...")
                .availableFrom("2023-01-01")
                .availableTo("2023-12-31")
                .type(ValidTypesOfHotelsEnum.LUXURY)
                .status(true)
                .build();

        hotelList.add(hotel);
        hotelList.add(hotel2);
    }

    @Test
    void getAllHotelsTest_Valid() {

        // when - action or behaviour that we are going test
        when(hotelRepository.findAll()).thenReturn(hotelList);

        assertEquals(2, hotelServiceImp.getAllHotels().size());

        verify(hotelRepository, times(1)).findAll();
    }


    @Test
    void saveHotelTest_Valid() {
        Hotel hotel3 = Hotel.builder()
                .name("Hilton Hotel")
                .description("5* Hotel...")
                .availableFrom("2023-01-01")
                .availableTo("2023-12-31")
                .type(ValidTypesOfHotelsEnum.DELUXE)
                .status(true)
                .build();

        when(hotelRepository.save(hotel3)).thenReturn(hotel3);

        // then - verify the result or output using assert statements
        assertAll(
                () -> assertEquals("Hilton Hotel", hotel3.getName()),
                () -> assertEquals("5* Hotel...", hotel3.getDescription()),
                () -> assertEquals(ValidTypesOfHotelsEnum.DELUXE, hotel3.getType())
        );
    }


}