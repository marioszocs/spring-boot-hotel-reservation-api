package dev.marioszocs.hotelreservationapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Column(name = "hotel_id", nullable = false)
    private UUID hotelId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reservation that = (Reservation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
