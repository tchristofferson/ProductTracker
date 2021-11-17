package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.exc.*;
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
    public Category addCategory(@RequestBody @Valid Category category) {
        if (category.getId() != null)
            throw new CategoryPostRequestIdException();

        return categoryService.save(category);
    }

    /* Properties */
    @PostMapping(path = "/properties")
    public Property addProperty(@RequestBody @Valid Property property) {
        if (property.getId() != null)
            throw new PropertyPostRequestIdException();

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
                throw new InvalidCategoryIdException(HttpStatus.CONFLICT);
        }

        categoryService.save(category);
        return category;
    }

    /* Property */
    @PutMapping(path = "/properties")
    public Property updateProperty(@RequestBody @Valid Property property) {
        if (property.getId() != null) {
            Optional<Property> optionalProperty = propertyService.getProperty(property.getId());

            if (optionalProperty.isEmpty())
                throw new InvalidPropertyIdException(HttpStatus.CONFLICT);
        }

        propertyService.saveProperty(property);
        return property;
    }

    /* PropertyLocation */
    @PutMapping(path = "/propertyLocations")
    public PropertyLocation updatePropertyLocation(@RequestBody @Valid PropertyLocation propertyLocation) {
        if (propertyLocation.getId() != null) {
            Optional<PropertyLocation> optionalPropertyLocation = propertyLocationService.getPropertyLocation(propertyLocation.getId());

            if (optionalPropertyLocation.isEmpty())
                throw new InvalidPropertyLocationIdException(HttpStatus.CONFLICT);
        }

        Property property = propertyLocation.getProperty();

        if (propertyLocation.getId() == null)
            throw new UnspecifiedPropertyIdException();

        Optional<Property> optionalProperty = propertyService.getProperty(property.getId());

        if (optionalProperty.isEmpty())
            throw new InvalidPropertyIdException(HttpStatus.NOT_FOUND);

        propertyLocation.setProperty(optionalProperty.get());
        propertyLocationService.savePropertyLocation(propertyLocation);
        return propertyLocation;
    }

    @PutMapping(path = "/products")
    public Product updateProduct(@RequestBody @Valid Product product) {
        if (product.getId() != null) {
            Optional<Product> optionalProduct = productService.getProduct(product.getId());

            if (optionalProduct.isEmpty())
                throw new InvalidProductId(HttpStatus.CONFLICT);
        }

        if (product.getPropertyLocation().getId() == null)
            throw new UnspecifiedPropertyLocationIdException();

        Optional<PropertyLocation> optionalPropertyLocation = propertyLocationService.getPropertyLocation(product.getPropertyLocation().getId());

        if (optionalPropertyLocation.isEmpty())
            throw new InvalidPropertyLocationIdException(HttpStatus.NOT_FOUND);

        if (product.getCategory() != null) {
            if (product.getCategory().getId() == null)
                throw new UnspecifiedCategoryIdException();

            Optional<Category> optionalCategory = categoryService.getCategory(product.getCategory().getId());

            if (optionalCategory.isEmpty())
                throw new InvalidCategoryIdException(HttpStatus.NOT_FOUND);

            product.setCategory(optionalCategory.get());
        }

        product.setPropertyLocation(optionalPropertyLocation.get());
        productService.saveProduct(product);
        return product;
    }

    /*
     * DELETE
     */

    @DeleteMapping(path = "/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("categoryId") long categoryId) {
        Optional<Category> optionalCategory = categoryService.getCategory(categoryId);

        if (optionalCategory.isEmpty())
            throw new InvalidCategoryIdException(HttpStatus.NOT_FOUND);

        categoryService.deleteCategory(categoryId);
    }

    @DeleteMapping(path = "/properties/{propertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable("propertyId") long propertyId) {
        Optional<Property> optionalProperty = propertyService.getProperty(propertyId);

        if (optionalProperty.isEmpty())
            throw new InvalidPropertyIdException(HttpStatus.NOT_FOUND);

        propertyService.deleteProperty(propertyId);
    }

    @DeleteMapping(path = "/propertyLocations/{propertyLocationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePropertyLocation(@PathVariable("propertyLocationId") long propertyLocationId) {
        Optional<PropertyLocation> optionalPropertyLocation = propertyLocationService.getPropertyLocation(propertyLocationId);

        if (optionalPropertyLocation.isEmpty())
            throw new InvalidPropertyLocationIdException(HttpStatus.NOT_FOUND);

        propertyLocationService.deletePropertyLocation(propertyLocationId);
    }

    @DeleteMapping(path = "/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") long productId) {
        Optional<Product> optionalProduct = productService.getProduct(productId);

        if (optionalProduct.isEmpty())
            throw new InvalidProductId(HttpStatus.NOT_FOUND);

        productService.deleteProduct(productId);
    }

}
