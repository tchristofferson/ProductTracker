package com.tchristofferson.homeproducts.repos;

import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Location;
import com.tchristofferson.homeproducts.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByLocationOrderByCategoryAsc(Location location);

    List<Product> findByCategoryOrderByCategoryAsc(Category category);

}
