package com.tchristofferson.homeproducts.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity(name = "PropertyLocation")
@Table(name = "PropertyLocations")
public class PropertyLocation {

    @Id
    @SequenceGenerator(name = "property_location_sequence", sequenceName = "property_location_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_location_sequence")
    @Column(name = "id", updatable = false)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_property_location_propertyId"))
    @JsonProperty("property")
    @NotNull(message = "Property must be set!")
    private Property property;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("name")
    @NotBlank(message = "Invalid property location name!")
    private String name;

    public PropertyLocation() {}

    public PropertyLocation(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PropertyLocation(Long id) {
        this.id = id;
    }

    public PropertyLocation(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
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

        PropertyLocation that = (PropertyLocation) o;
        return Objects.equals(id, that.id) && Objects.equals(property, that.property) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, property, name);
    }
}
