package dev.marioszocs.hotelreservationapi.controller;

import dev.marioszocs.hotelreservationapi.dto.IdEntity;
import dev.marioszocs.hotelreservationapi.dto.SuccessEntity;
import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.service.HotelService;
import dev.marioszocs.hotelreservationapi.validator.HotelValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Hotel Controller containing endpoints of Hotel related API calls
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class HotelController {
    private final HotelService hotelService;

    /**
     * End point to get all Hotels in the database
     *
     * @return list of Hotels
     */
    @GetMapping(value = "/hotels", produces = "application/json")
    public ResponseEntity<List<Hotel>> getHotelList(){
        log.info("Get all: {} hotels from database", hotelService.getAllHotels().size());
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    /**
     * End point to get user specified Hotel
     *
     * @param id Integer
     * @return
     */
    @GetMapping(value = "/hotel/{id}", produces = "application/json")
    public Hotel getHotel(@PathVariable UUID id) {
        HotelValidator.validateId(id);
        log.info("Get hotel by id = {}", id);
        return hotelService.getHotel(id);
    }

    /**
     * End point to get list of Hotels available between user specified dates
     *
     * @param from String
     * @param to String
     * @return list of Hotels
     */
    @GetMapping(value = "/hotels/availabilitySearch", produces = "application/json")
    public List<Hotel> getHotel(@RequestParam("dateFrom") String from, @RequestParam("dateTo") String to){
        HotelValidator.validateDates(from, to);
        log.info("Get Hotel between dates from: {} -> to: {}", from, to);
        return hotelService.getAvailable(from, to);
    }

    /**
     * End point to update user specified Hotel.
     *
     * @param hotel Hotel
     * @return successEntity
     */
    @PatchMapping(value = "/hotel", produces = "application/json")
    public SuccessEntity patchHotel(@RequestBody @Valid Hotel hotel){
        HotelValidator.validateHotelPATCH(hotel);
        log.info("Update Hotel with name: {}", hotel.getName());
        return hotelService.patchHotel(hotel);
    }

    /**
     * End point to save a user specified hotel
     *
     * @param hotel Hotel
     * @return idEntity
     */
    @PostMapping(value = "/hotel", produces = "application/json")
    public IdEntity saveHotel(@RequestBody @Valid Hotel hotel){
        HotelValidator.validateHotelPOST(hotel);
        log.info("Save a user specified hotel with name: {}", hotel.getName());
        return hotelService.saveHotel(hotel);
    }

    /**
     * End point to delete a user specified hotel
     *
     * @param id Integer
     * @return SuccessEntity
     */
    @DeleteMapping(value = "/hotel/{id}", produces = "application/json")
    public SuccessEntity deleteHotel(@PathVariable UUID id){
        HotelValidator.validateId(id);
        log.info("Delete a user specified hotel with id = {}", id);
        return hotelService.deleteHotel(id);
    }
}
