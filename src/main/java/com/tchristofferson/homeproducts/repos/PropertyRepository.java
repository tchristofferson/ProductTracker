package com.tchristofferson.homeproducts.repos;

import com.tchristofferson.homeproducts.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByNameContaining(String name);

}
