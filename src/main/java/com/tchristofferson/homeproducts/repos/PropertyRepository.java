package com.tchristofferson.homeproducts.repos;

import com.tchristofferson.homeproducts.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
