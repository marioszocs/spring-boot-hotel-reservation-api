package dev.marioszocs.hotelreservationapi.repository;

import dev.marioszocs.hotelreservationapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    @Query(value = "SELECT hotel.* FROM HOTEL AS hotel " +
            "WHERE hotel.DT_AVAILABLE_FROM <= :dateFrom AND hotel.DT_AVAILABLE_TO >= :dateTo " +
            "AND hotel.ID NOT IN " +
            "(SELECT HOTEL_ID FROM RESERVATION WHERE (DT_CHECK_IN > :dateFrom OR DT_CHECK_OUT < :dateTo))", nativeQuery = true)
    List<Hotel> findAllBetweenDates(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);

    @Query(value = "SELECT * FROM hotel WHERE id = ?1", nativeQuery = true)
    UUID existsHotelByUUID(@Param("id") String id);

}
