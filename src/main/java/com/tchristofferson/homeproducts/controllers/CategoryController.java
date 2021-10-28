package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

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

    //TODO: Use pagination
    @GetMapping
    public String getCategories(Model model) {
        Iterable<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);

        return "categories";
    }

    @PostMapping(path = "/new")
    public String addCategory(@RequestParam("name") @NotBlank String name) {
        categoryService.save(new Category(name));

        return "redirect:/categories";
    }
}
