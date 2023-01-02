package dev.marioszocs.hotelreservationapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Use @MappedSuperclass to indicate that although this is not an Entity in itself,
 * i.e. we don't want to store it in a separate table, but the 3 fields shown here
 * should appear in the tables of the child classes
 */
@MappedSuperclass
@Getter
@JsonIgnoreProperties(value = {"createdDate", "lastModifiedDate"})
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date", nullable = false)
    private Timestamp lastModifiedDate;
}
