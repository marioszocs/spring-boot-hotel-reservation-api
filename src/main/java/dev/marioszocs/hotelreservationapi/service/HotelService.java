
package dev.marioszocs.hotelreservationapi.service;

import dev.marioszocs.hotelreservationapi.dto.IdEntity;
import dev.marioszocs.hotelreservationapi.dto.SuccessEntity;
import dev.marioszocs.hotelreservationapi.entity.Hotel;

import java.util.List;
import java.util.UUID;

public interface HotelService {

    List<Hotel> getAllHotels();
    Hotel getHotel(UUID id);
    List<Hotel> getAvailable(String dateFrom, String dateTo);
    IdEntity saveHotel(Hotel hotel);
    SuccessEntity deleteHotel(UUID id);
    SuccessEntity patchHotel(Hotel hotel);
    void doesReservationOverlap(Hotel hotel);
    boolean validateHotelExistenceById(UUID id);
}
