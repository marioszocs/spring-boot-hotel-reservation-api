package dev.marioszocs.hotelreservationapi.controller;

import dev.marioszocs.hotelreservationapi.constants.AppConstants;
import dev.marioszocs.hotelreservationapi.dto.IdEntity;
import dev.marioszocs.hotelreservationapi.dto.SuccessEntity;
import dev.marioszocs.hotelreservationapi.entity.Hotel;
import dev.marioszocs.hotelreservationapi.service.HotelService;
import dev.marioszocs.hotelreservationapi.validator.HotelValidator;
import dev.marioszocs.hotelreservationapi.validator.PageNumberAndSizeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Hotel Controller containing endpoints of Hotel related API calls
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
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
     * End point to get Hotel paged list
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @return
     */
    @GetMapping(value = "/hotelPagedList", produces = "application/json")
    public ResponseEntity<List<Hotel>> getPagedHotelList(
            @RequestParam(name = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORTING_PARAM) String sortBy) {

        PageNumberAndSizeValidator.validatePageNumberAndSize(pageNumber, pageSize);
        List<Hotel> hotelPagedList = hotelService.getHotelPagedList(pageNumber, pageSize, sortBy);

        log.info("Return Hotel paged list with pageNumber: {}, pageSize: {} and sortBy: {}.", pageNumber, pageSize, sortBy);

        return new ResponseEntity<>(hotelPagedList, HttpStatus.OK);
    }

    /**
     * End point to get user specified Hotel
     *
     * @param id Integer
     * @return
     */
    @GetMapping(value = "/hotel/{id}", produces = "application/json")
    public Hotel getHotel(@PathVariable Integer id) {
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
        log.info("Get all Hotels available between dates from: {} to: {}", from, to);
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
    public SuccessEntity deleteHotel(@PathVariable Integer id){
        HotelValidator.validateId(id);
        log.info("Delete a user specified hotel with id = {}", id);
        return hotelService.deleteHotel(id);
    }
}
