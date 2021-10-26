package com.tchristofferson.homeproducts.repos;

import com.tchristofferson.homeproducts.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("locationRepository")
public interface LocationRepository extends JpaRepository<Location, Long> {
}