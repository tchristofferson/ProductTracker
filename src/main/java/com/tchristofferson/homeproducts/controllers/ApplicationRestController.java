package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.services.CategoryService;
import com.tchristofferson.homeproducts.services.ProductService;
import com.tchristofferson.homeproducts.services.PropertyLocationService;
import com.tchristofferson.homeproducts.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
public class ApplicationRestController {

    private final CategoryService categoryService;
    private final PropertyService propertyService;
    private final PropertyLocationService propertyLocationService;
    private final ProductService productService;

    @Autowired
    public ApplicationRestController(CategoryService categoryService, PropertyService propertyService, PropertyLocationService propertyLocationService, ProductService productService) {
        this.categoryService = categoryService;
        this.propertyService = propertyService;
        this.propertyLocationService = propertyLocationService;
        this.productService = productService;

        //TODO: Remove
        Category category = new Category("Lights");
        categoryService.save(category);

        Property property = new Property("Home");
        propertyService.saveProperty(property);

        PropertyLocation propertyLocation = new PropertyLocation("Kitchen");
        propertyLocation.setProperty(property);
        propertyLocationService.savePropertyLocation(propertyLocation);

        Product product = new Product("Light Bulb");
        product.setPropertyLocation(propertyLocation);
        product.setCategory(category);
        productService.saveProduct(product);
    }

    /*
     * GET
     */

    /* Categories */

    //Get all categories
    @GetMapping(path = "/categories")
    public Iterable<Category> getCategories() {
        return categoryService.getCategories();
    }

    //Get category by id
    @GetMapping(path = "/categories/{categoryId}")
    public Optional<Category> getCategory(@PathVariable("categoryId") long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    /* Properties */

    //Get all properties
    @GetMapping(path = "/properties")
    public List<Property> getProperties() {
        return propertyService.getProperties();
    }

    //Get property by id
    @GetMapping(path = "/properties/{propertyId}")
    public Optional<Property> getProperty(@PathVariable("propertyId") long propertyId) {
        return propertyService.getProperty(propertyId);
    }

    /* PropertyLocations */

    //Get all property locations by property
    @GetMapping(path = "/propertyLocations")
    public List<PropertyLocation> getPropertyLocations(@RequestParam("propertyId") long propertyId) {
        return propertyLocationService.getPropertyLocations(propertyId);
    }

    //Get property location by id
    @GetMapping(path = "/propertyLocations/{propertyLocationId}")
    public Optional<PropertyLocation> getPropertyLocation(@PathVariable("propertyLocationId") long propertyLocationId) {
        return propertyLocationService.getPropertyLocation(propertyLocationId);
    }

    /* Products */

    //Get all products by property location
    @GetMapping(path = "/products")
    public List<Product> getProducts(@RequestParam("propertyLocationId") long propertyLocationId) {
        return productService.getProducts(propertyLocationId);
    }

    //Get product by id
    @GetMapping(path = "/products/{productId}")
    public Optional<Product> getProduct(@PathVariable("productId") long productId) {
        return productService.getProduct(productId);
    }

    /* POST */

    

    /* PUT */



    /* DELETE */


}
