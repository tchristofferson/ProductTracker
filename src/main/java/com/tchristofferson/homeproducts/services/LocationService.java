package com.tchristofferson.homeproducts.services;

import com.tchristofferson.homeproducts.repos.LocationRepository;
import com.tchristofferson.homeproducts.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Page<Location> getLocationPage(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    public Optional<Location> getLocation(Long locationId) {
        return locationRepository.findById(locationId);
    }

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

}
