package com.tchristofferson.homeproducts.services;

import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.repos.PropertyLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PropertyLocationService {

    private final PropertyLocationRepository propertyLocationRepository;

    @Autowired
    public PropertyLocationService(PropertyLocationRepository propertyLocationRepository) {
        this.propertyLocationRepository = propertyLocationRepository;
    }

    public Page<PropertyLocation> getPropertyLocations(Pageable pageable, Long propertyId) {
        Property property = new Property();
        property.setId(propertyId);

        return propertyLocationRepository.findAllByPropertyOrderByPropertyAsc(pageable, property);
    }

    public void savePropertyLocation(PropertyLocation propertyLocation) {
        propertyLocationRepository.save(propertyLocation);
    }
}
