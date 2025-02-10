package dev.marioszocs.hotelreservationapi.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Use @MappedSuperclass to indicate that although this is not an Entity in itself,
 * i.e. we don't want to store it in a separate table, but the 3 fields shown here
 * should appear in the tables of the child classes
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    protected Integer id;
}
