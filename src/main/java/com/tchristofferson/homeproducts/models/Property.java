package com.tchristofferson.homeproducts.models;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Property")
@Table(name = "Properties")
public class Property {

    @Id
    @SequenceGenerator(name = "property_sequence", sequenceName = "property_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    //Used by JPA
    public Property() {}

    public Property(String name) {
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

        Property property = (Property) o;
        return Objects.equals(id, property.id) && Objects.equals(name, property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
