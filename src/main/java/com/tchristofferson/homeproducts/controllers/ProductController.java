package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.exc.InvalidIdException;
import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.services.CategoryService;
import com.tchristofferson.homeproducts.services.ProductService;
import com.tchristofferson.homeproducts.services.PropertyLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PropertyLocationService propertyLocationService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, PropertyLocationService propertyLocationService, CategoryService categoryService) {
        this.productService = productService;
        this.propertyLocationService = propertyLocationService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProducts() {
        return "redirect:/properties";
    }

    @GetMapping(path = "/{propertyLocationId}")
    public String getProducts(Model model, @PathVariable("propertyLocationId") Long propertyLocationId) {
        return getProducts(model, propertyLocationId, 1);
    }

    //TODO: Pagination
    //Gets categories and products since they are displayed on the same page
    @GetMapping(path = "/{propertyLocationId}/page/{page}")
    public String getProducts(Model model, @PathVariable("propertyLocationId") Long propertyLocationId, @PathVariable("page") int page) {
        Optional<PropertyLocation> optionalPropertyLocation = propertyLocationService.getPropertyLocation(propertyLocationId);

        if (optionalPropertyLocation.isEmpty())
            throw new InvalidIdException("Invalid property location id!");

        PropertyLocation propertyLocation = optionalPropertyLocation.get();
        Map<Category, List<Product>> categoryProductMap = productService.getCategoryProductMap(propertyLocation);
        model.addAttribute("categoryProductMap", categoryProductMap);
        model.addAttribute("propertyLocation", propertyLocation);

        return "products";
    }

    @GetMapping(path = "/{propertyLocationId}/new")
    public String createProduct(Model model, @PathVariable("propertyLocationId") long propertyLocationId) {
        Optional<PropertyLocation> optionalPropertyLocation = propertyLocationService.getPropertyLocation(propertyLocationId);

        if (optionalPropertyLocation.isEmpty())
            throw new InvalidIdException("Invalid property location id!");

        Iterable<Category> categories = categoryService.getCategories();
        PropertyLocation propertyLocation = optionalPropertyLocation.get();
        model.addAttribute("propertyLocation", propertyLocation);
        model.addAttribute("categories", categories);

        return "edit-product";
    }

    @GetMapping(path = "/{productId}/edit")
    public String editProduct(Model model, @PathVariable("productId") long productId) {
        Optional<Product> optionalProduct = productService.getProduct(productId);

        if (optionalProduct.isEmpty())
            throw new InvalidIdException("Invalid product id!");

        Iterable<Category> categories = categoryService.getCategories();
        Product product = optionalProduct.get();
        model.addAttribute("product", product);
        model.addAttribute("propertyLocation", product.getPropertyLocation());
        model.addAttribute("categories", categories);

        return "edit-product";
    }

    @PostMapping(path = "/{propertyLocationId}/new")
    public String addProduct(
            @PathVariable("propertyLocationId") long propertyLocationId,
            @RequestParam("category") @Nullable Long categoryId,
            @RequestParam("name") @NotBlank String name,
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

        return "redirect:/products/" + propertyLocationId;
    }

    @PostMapping(path = "/{productId}/edit")
    public String editProduct(
            @PathVariable("productId") long productId,
            @RequestParam("category") @Nullable Long categoryId,
            @RequestParam("name") @NotBlank String name,
            @RequestParam("productNumber") @Nullable String productNumber,
            @RequestParam("link") @Nullable String link,
            @RequestParam("inventory") @Nullable Integer inventory) {
        Optional<Product> optionalProduct = productService.getProduct(productId);

        if (optionalProduct.isEmpty())
            throw new InvalidIdException("Invalid product id!");

        Product product = optionalProduct.get();
        product.setName(name);
        product.setProductNumber(productNumber);
        product.setLink(link);
        product.setInventory(inventory);

        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);

        try {
            productService.saveProduct(product);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidIdException("Invalid category id!");
        }

        return "redirect:/products/" + product.getPropertyLocation().getId();
    }
}
