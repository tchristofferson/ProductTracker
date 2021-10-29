package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.exc.InvalidIdException;
import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;

        //TODO: Remove
        categoryService.save(new Category("Lights"));
    }

    @GetMapping
    public String getCategories(Model model, @RequestParam("search") @Nullable String search) {
        Iterable<Category> categories;
        boolean isSearch = false;

        if (search == null || search.isBlank()) {
            categories = categoryService.getCategories();
        } else {
            categories = categoryService.getCategories(search);
            isSearch = true;
        }

        model.addAttribute("categories", categories);
        model.addAttribute("isSearch", isSearch);
        return "categories";
    }

    @GetMapping(path = "/new")
    public String addCategory() {
        return "edit-category";
    }

    @GetMapping(path = "/edit/{categoryId}")
    public String editCategory(Model model, @PathVariable("categoryId") Long categoryId) {
        Optional<Category> optionalCategory = categoryService.getCategory(categoryId);

        if (optionalCategory.isEmpty())
            throw new InvalidIdException("Invalid category id!");

        model.addAttribute("category", optionalCategory.get());

        return "edit-category";
    }

    @PostMapping(path = "/new")
    public String addCategory(@RequestParam("categoryName") @NotBlank String name) {
        categoryService.save(new Category(name));

        return "redirect:/categories";
    }

    /* This is more of a PUT request, but HTML form only allows POST. Could be done using JavaScript */
    @PostMapping(path = "/edit")
    public String editCategory(Model model, @RequestParam("id") long categoryId, @RequestParam("categoryName") @NotBlank String name) {
        Category category = new Category(name);
        category.setId(categoryId);

        try {
            categoryService.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidIdException("Invalid category id!");
        }

        return getCategories(model, null);
    }

    /* This is more of a DELETE request, but HTML only allows POST */
    @PostMapping(path = "/delete")
    public String deleteCategory(@RequestParam("categoryId") long categoryId) {
        categoryService.deleteCategory(categoryId);
        return "redirect:/categories";
    }
}
