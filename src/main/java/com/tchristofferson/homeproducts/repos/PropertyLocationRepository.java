package com.tchristofferson.homeproducts.repos;

import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyLocationRepository extends JpaRepository<PropertyLocation, Long> {

    Page<PropertyLocation> findAllByPropertyOrderByPropertyAsc(Pageable pageable, Property property);

}
