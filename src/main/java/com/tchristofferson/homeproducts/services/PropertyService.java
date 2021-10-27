package com.tchristofferson.homeproducts.services;

import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.repos.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Page<Property> getPropertyPage(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }

    public Optional<Property> getProperty(Long propertyId) {
        return propertyRepository.findById(propertyId);
    }

    public void saveProperty(Property property) {
        propertyRepository.save(property);
    }

    public void deleteProperty(Long propertyId) {
        Property property = new Property();
        property.setId(propertyId);

        propertyRepository.delete(property);
    }

}
