package dev.marioszocs.hotelreservationapi.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel")
public class Hotel extends BaseEntity{

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;*/

    @Column
    private String name;

    @Column
    private ValidTypesOfHotelsEnum type = ValidTypesOfHotelsEnum.DELUXE;

    @Column
    private String description;

    @Column(name = "available_from")
    private String availableFrom;

    @Column(name = "available_to")
    private String availableTo;

    @Column
    private boolean status;

}
