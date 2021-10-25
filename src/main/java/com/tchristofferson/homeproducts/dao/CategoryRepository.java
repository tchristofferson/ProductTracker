package com.tchristofferson.homeproducts.dao;

import com.tchristofferson.homeproducts.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
