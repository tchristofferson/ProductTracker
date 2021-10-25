package com.tchristofferson.homeproducts.dao;

import com.tchristofferson.homeproducts.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
