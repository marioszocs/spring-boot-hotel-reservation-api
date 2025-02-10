package dev.marioszocs.hotelreservationapi.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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
public class Hotel extends AuditableEntity {

    @NotBlank(message = "Hotel name is mandatory")
    @Size(min = 3, max = 40, message = "Name must be at least 3 characters long")
    @Column()
    private String name;

    //@NotEmpty
    @Column
    private ValidTypesOfHotelsEnum type = ValidTypesOfHotelsEnum.DELUXE;

    @NotBlank(message = "Description is mandatory")
    @Column
    private String description;

    @Column(name = "available_from")
    private String availableFrom;

    @Column(name = "available_to")
    private String availableTo;

    @Column(nullable = false)
    private boolean status;

}
