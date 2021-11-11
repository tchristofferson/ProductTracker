package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.exc.InvalidCategoryIdException;
import com.tchristofferson.homeproducts.exc.InvalidPropertyIdException;
import com.tchristofferson.homeproducts.exc.InvalidPropertyLocationIdException;
import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.services.CategoryService;
import com.tchristofferson.homeproducts.services.ProductService;
import com.tchristofferson.homeproducts.services.PropertyLocationService;
import com.tchristofferson.homeproducts.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin("http://localhost:3000")
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

    /*
     * POST
     */

    /* Categories */
    @PostMapping(path = "/categories")
    public Category addCategory(@RequestBody Category category) {
        category.setId(null);
        return categoryService.save(category);
    }

    /* Properties */
    @PostMapping(path = "/properties")
    public Property addProperty(@RequestBody Property property) {
        property.setId(null);
        return propertyService.saveProperty(property);
    }

    /* PropertyLocation */
    @PostMapping(path = "/propertyLocations/{propertyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyLocation addPropertyLocation(@PathVariable("propertyId") long propertyId, @RequestBody PropertyLocation propertyLocation) {
        Optional<Property> optionalProperty = propertyService.getProperty(propertyId);

        if (optionalProperty.isEmpty())
            throw new InvalidPropertyIdException(HttpStatus.NOT_FOUND);

        propertyLocation.setId(null);
        propertyLocation.setProperty(optionalProperty.get());
        propertyLocationService.savePropertyLocation(propertyLocation);
        return propertyLocation;
    }

    /* Product */
    @PostMapping(path = "/products/{propertyLocationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@PathVariable("propertyLocationId") long propertyLocationId, @RequestBody @Valid Product product) {
        Optional<PropertyLocation> optionalPropertyLocation = propertyLocationService.getPropertyLocation(propertyLocationId);

        if (optionalPropertyLocation.isEmpty())
            throw new InvalidPropertyLocationIdException(HttpStatus.NOT_FOUND);

        if (product.getCategory() != null) {
            Optional<Category> optionalCategory = categoryService.getCategory(product.getCategory().getId());

            if (optionalCategory.isEmpty())
                throw new InvalidCategoryIdException(HttpStatus.CONFLICT);

            product.setCategory(optionalCategory.get());
        }

        product.setId(null);
        product.setPropertyLocation(optionalPropertyLocation.get());
        productService.saveProduct(product);

        return product;
    }

    /*
     * PUT
     */

    /* Category */
    @PutMapping(path = "/categories")
    public Category updateCategory(@RequestBody @Valid Category category) {
        if (category.getId() != null) {
            Optional<Category> optionalCategory = categoryService.getCategory(category.getId());

            if (optionalCategory.isEmpty())
                throw new InvalidCategoryIdException(HttpStatus.NOT_FOUND);
        }

        categoryService.save(category);
        return category;
    }

    /*
     * DELETE
     */


}
