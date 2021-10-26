package com.tchristofferson.homeproducts.services;

import com.tchristofferson.homeproducts.repos.CategoryRepository;
import com.tchristofferson.homeproducts.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> getCategories(Iterable<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }
}
