package com.tchristofferson.homeproducts.repos;

import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByPropertyLocationOrderByCategoryAsc(PropertyLocation propertyLocation);

    List<Product> findByCategoryOrderByCategoryAsc(Category category);

    List<Product> findByNameContaining(String name);
}
