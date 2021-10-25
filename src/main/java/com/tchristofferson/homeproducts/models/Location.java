package com.tchristofferson.homeproducts.models;

import javax.persistence.*;
import java.util.Objects;

//This class doesn't actually contain the products for the location (stored in database)
@Entity(name = "Location")
@Table(name = "Locations")
public class Location {

    @Id
    @SequenceGenerator(name = "location_sequence", sequenceName = "location_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    //Used by JPA
    public Location() {}

    public Location(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Location location = (Location) o;
        return id.equals(location.id) && name.equals(location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
