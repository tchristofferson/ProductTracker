package com.tchristofferson.homeproducts.services;

import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.repos.PropertyLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyLocationService {

    private final PropertyLocationRepository propertyLocationRepository;

    @Autowired
    public PropertyLocationService(PropertyLocationRepository propertyLocationRepository) {
        this.propertyLocationRepository = propertyLocationRepository;
    }

    public Optional<PropertyLocation> getPropertyLocation(long id) {
        return propertyLocationRepository.findById(id);
    }

    public List<PropertyLocation> getPropertyLocations(long propertyId) {
        Property property = new Property(propertyId);
        return propertyLocationRepository.findAllByPropertyOrderByPropertyAsc(property);
    }

    public Page<PropertyLocation> getPropertyLocations(Pageable pageable, long propertyId) {
        Property property = new Property();
        property.setId(propertyId);

        return propertyLocationRepository.findAllByPropertyOrderByPropertyAsc(pageable, property);
    }

    public PropertyLocation savePropertyLocation(PropertyLocation propertyLocation) {
        return propertyLocationRepository.save(propertyLocation);
    }

    public void deletePropertyLocation(long propertyLocationId) {
        propertyLocationRepository.deleteById(propertyLocationId);
    }
}
