package dev.marioszocs.hotelreservationapi.serviceImp;

import dev.marioszocs.hotelreservationapi.constant.ErrorMessages;
import dev.marioszocs.hotelreservationapi.dto.IdEntity;
import dev.marioszocs.hotelreservationapi.dto.SuccessEntity;
import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.entity.Reservation;
import dev.marioszocs.hotelreservationapi.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import dev.marioszocs.hotelreservationapi.repository.HotelRepository;
import dev.marioszocs.hotelreservationapi.repository.ReservationRepository;
import dev.marioszocs.hotelreservationapi.service.HotelService;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hotel Service that preforms operations regarding Hotel API Calls
 */
@Slf4j
@Service
@Transactional // TODO this is jakarta not javax...
public class HotelServiceImp implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Return all existing Hotel objects in the database
     * @return List<Hotel>
     */
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    /**
     * Returns a user specified Hotel item through the Hotel id
     *
     * @param id
     * @return Hotel
     */
    @Override
    public Hotel getHotel(Integer id) {
        validateExistence(id);
        return hotelRepository.findById(id).get();
    }

    /**
     * Returns all Hotel objects in the database that are available in between user specified dates
     *
     * @param dateFrom
     * @param dateTo
     * @return
     */
    @Override
    public List<Hotel> getAvailable(String dateFrom, String dateTo) {
        return hotelRepository.findAllBetweenDates(dateFrom, dateTo);
    }

    /**
     * Saves a user specified Hotel object to the database
     *
     * @param hotel
     * @return
     */
    @Override
    public IdEntity saveHotel(Hotel hotel) {
        //If dates are empty strings make them null values so that they can be accepted by the database
        if ((!StringUtils.hasText(hotel.getAvailableFrom())) && (!(StringUtils.hasText(hotel.getAvailableTo())))) {
            hotel.setAvailableFrom(null);
            hotel.setAvailableTo(null);
        }
        hotel = hotelRepository.save(hotel);
        IdEntity idEntity = new IdEntity();
        idEntity.setId(hotel.getId());
        return idEntity;
    }

    /**
     * Deletes a user specified Hotel object from the database
     * @param id
     * @return
     */
    @Override
    public SuccessEntity deleteHotel(Integer id) {
        validateExistence(id);
        if(reservationRepository.findAll().stream()
                .anyMatch(reservations -> reservations.getHotelId() == id)){
            throw new InvalidRequestException(ErrorMessages.INVALID_HOTEL_DELETE);
        }
        SuccessEntity successEntity = new SuccessEntity();
        hotelRepository.deleteById(id);
        successEntity.setSuccess(!hotelRepository.existsById(id));
        return successEntity;
    }

    /**
     * Updates a pre-existing Hotel object in the database
     * @param hotel
     * @return
     */
    @Override
    public SuccessEntity patchHotel(Hotel hotel) {
        validateExistence(hotel.getId());
        doesReservationOverlap(hotel);
        SuccessEntity successEntity = new SuccessEntity();
        hotel = hotelRepository.save(hotel);
        successEntity.setSuccess(hotelRepository.existsById(hotel.getId()));
        return successEntity;
    }

    /**
     * Checks to see if a reservation date overlaps with the inventory dates
     * @param hotel
     */
    @Override
    public void doesReservationOverlap(Hotel hotel) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String availTo = hotel.getAvailableTo();
        String availFrom = hotel.getAvailableFrom();
        Integer inventoryId = hotel.getId();
        List<Reservation> matchingReservationList = reservationRepository.findAll().stream().filter(reservations -> {
            if (reservations.getHotelId() == inventoryId) {
                try {
                    //Checks to see if the user dates are null, if so throw an error as it conflicts with a reservation
                    if (!StringUtils.hasText(availTo) && !StringUtils.hasText(availFrom)) {
                        throw new InvalidRequestException(ErrorMessages.INVALID_DATE_CHANGE_NULL);
                    }
                    //should return 1 or 0 if there is no overlap, should return -1 if there is an overlap
                    int checkInBeforeAvailFrom = sdf.parse(reservations.getCheckIn()).compareTo(sdf.parse(availFrom));
                    //should return -1 or 0 if there is no overlap, should return 1 if there is an overlap
                    int checkOutBeforeAvailTo = sdf.parse(reservations.getCheckOut()).compareTo(sdf.parse(availTo));
                    if ((checkInBeforeAvailFrom < 0) || (checkOutBeforeAvailTo > 0)) {
                        return true;
                    }

                } catch (ParseException e) {
                    throw new InvalidRequestException(ErrorMessages.PARSE_ERROR);
                }
            }
            return false;
        }).collect(Collectors.toList());

        if (matchingReservationList.size() != 0) {
            throw new InvalidRequestException(ErrorMessages.INVALID_HOTEL_UPDATE);
        }
    }

    /**
     * Checks the existence of a Hotel object in the database
     *
     * @param id
     * @return
     */
    @Override
    public boolean validateExistence(Integer id) {
        if (!hotelRepository.existsById(id)){
            throw new InvalidRequestException(ErrorMessages.INVALID_ID_EXISTENCE);
        } else {
            return true;
        }
    }
}
