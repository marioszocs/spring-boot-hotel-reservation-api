package dev.marioszocs.hotelreservationapi.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @NotEmpty
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
