package dev.marioszocs.hotelreservationapi.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation extends AuditableEntity {

    @Column(name = "hotel_id", nullable = false)
    private Integer hotelId;

    @Column(name = "check_in", nullable = false)
    private String checkIn;

    @Column(name = "check_out", nullable = false)
    private String checkOut;

    @Min(1)
    @Max(8)
    @Column(nullable = false)
    private Integer guests;

    @Column
    private boolean status;

}
