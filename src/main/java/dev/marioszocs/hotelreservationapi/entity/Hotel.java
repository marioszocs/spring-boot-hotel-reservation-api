package dev.marioszocs.hotelreservationapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Hotel Object
 */

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel")
public class Hotel extends BaseEntity {

    @NotBlank(message = "Hotel name is mandatory")
    @Size(min = 3, max = 40, message = "Name must be at least 3 characters long")
    @Column()
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private ValidTypesOfHotelsEnum type = ValidTypesOfHotelsEnum.DELUXE;

    @NotBlank(message = "Description is mandatory")
    @Column
    private String description;

    @Column(name = "available_from", nullable = false)
    private String availableFrom;

    @Column(name = "available_to", nullable = false)
    private String availableTo;

    @Column(nullable = false)
    private boolean status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hotel hotel = (Hotel) o;
        return getId() != null && Objects.equals(getId(), hotel.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
