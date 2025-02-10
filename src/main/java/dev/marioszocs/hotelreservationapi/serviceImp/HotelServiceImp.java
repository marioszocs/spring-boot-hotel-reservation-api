package dev.marioszocs.hotelreservationapi.serviceImp;

import dev.marioszocs.hotelreservationapi.constants.ErrorMessages;
import dev.marioszocs.hotelreservationapi.dto.IdEntity;
import dev.marioszocs.hotelreservationapi.dto.SuccessEntity;
import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.entity.Reservation;
import dev.marioszocs.hotelreservationapi.exception.InvalidRequestException;
import dev.marioszocs.hotelreservationapi.repository.HotelRepository;
import dev.marioszocs.hotelreservationapi.repository.ReservationRepository;
import dev.marioszocs.hotelreservationapi.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Hotel Service that preforms operations regarding Hotel API Calls
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImp implements HotelService {

    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;

    /**
     * Return all existing Hotel objects in the database
     *
     * @return List<Hotel>
     */
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    /**
     * Return existing Hotel with pagination
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     */
    @Override
    public List<Hotel> getHotelPagedList(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, sortBy);
        Page<Hotel> pagedResult = hotelRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Returns a user specified Hotel item through the Hotel id
     *
     * @param id
     * @return Hotel
     */
    @Override
    public Hotel getHotel(Integer id) {
        validateHotelExistenceById(id);
        return hotelRepository.findById(id).get();

        // Ot without validateHotelExistenceById(id);
        // Optional<Hotel> hotel = hotelRepository.findById(id);
        // return hotel.isPresent() ? hotel.get() : null;
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
    public IdEntity saveHotel(@Valid Hotel hotel) {
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
     *
     * @param id
     * @return
     */
    @Override
    public SuccessEntity deleteHotel(Integer id) {
        validateHotelExistenceById(id);
        if (reservationRepository.findAll().stream()
                .anyMatch(reservations -> reservations.getHotelId().equals(id))) {
            throw new InvalidRequestException(ErrorMessages.INVALID_HOTEL_DELETE);
        }
        SuccessEntity successEntity = new SuccessEntity();
        hotelRepository.deleteById(id);
        successEntity.setSuccess(!hotelRepository.existsById(id));
        return successEntity;
    }

    /**
     * Updates a pre-existing Hotel object in the database
     *
     * @param hotel
     * @return
     */
    @Override
    public SuccessEntity patchHotel(Hotel hotel) {
        validateHotelExistenceById(hotel.getId());
        doesReservationOverlap(hotel);
        SuccessEntity successEntity = new SuccessEntity();
        hotel = hotelRepository.save(hotel);
        successEntity.setSuccess(hotelRepository.existsById(hotel.getId()));
        return successEntity;
    }

    /**
     * Checks to see if a reservation date overlaps with the inventory dates
     *
     * @param hotel
     */
    @Override
    public void doesReservationOverlap(Hotel hotel) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String availTo = hotel.getAvailableTo();
        String availFrom = hotel.getAvailableFrom();
        Integer hotelId = hotel.getId();
        List<Reservation> matchingReservationList = reservationRepository.findAll().stream().filter(reservations -> {
            if (reservations.getHotelId() == hotelId) {
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
        }).toList();

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
    public boolean validateHotelExistenceById(Integer id) {
        if (!hotelRepository.existsById(id)) {
            log.error("Invalid ID: The entered id = {} does not exist.", id);
            throw new InvalidRequestException(ErrorMessages.INVALID_ID_EXISTENCE);
        } else {
            return true;
        }
    }
}
