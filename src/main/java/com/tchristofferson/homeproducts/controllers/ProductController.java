package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.exc.InvalidIdException;
import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts() {
        return "redirect:/properties";
    }

    @PostMapping(path = "/new")
    public String addProduct(
            @RequestParam("propertyLocation") long propertyLocationId,
            @RequestParam("category") @Nullable Long categoryId,
            @RequestParam("name") String name,
            @RequestParam("productNumber") @Nullable String productNumber,
            @RequestParam("link") @Nullable String link,
            @RequestParam("inventory") @Nullable Integer inventory) {
        Product product = new Product(name);
        product.setProductNumber(productNumber);
        product.setLink(link);
        product.setInventory(inventory);

        PropertyLocation propertyLocation = new PropertyLocation();
        propertyLocation.setId(propertyLocationId);
        product.setPropertyLocation(propertyLocation);

        if (categoryId != null) {
            Category category = new Category();
            category.setId(categoryId);
            product.setCategory(category);
        }

        try {
            productService.saveProduct(product);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidIdException("Invalid property location or category id!");
        }

        return "redirect:/properties";
    }
}
