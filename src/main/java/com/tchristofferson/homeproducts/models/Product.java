package com.tchristofferson.homeproducts.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Entity(name = "Product")
@Table(name = "Products")
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @Column(name = "id", updatable = false)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_location_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_product_property_location"))
    @JsonProperty("propertyLocation")
    @NotNull
    private PropertyLocation propertyLocation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "Fk_product_categoryId"))
    @JsonProperty("category")
    private Category category;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("name")
    @NotBlank(message = "Invalid product name!")
    private String name;

    @Column(name = "product_number", columnDefinition = "TEXT")
    @JsonProperty("productNumber")
    private String productNumber;

    @Column(name = "link", columnDefinition = "TEXT")
    @JsonProperty("link")
    private String link;

    @Column(name = "inventory")
    @JsonProperty("inventory")
    @PositiveOrZero(message = "Inventory must be positive")
    private Integer inventory;

    public Category getCategory() {
        return category;
    }

    public Product() {}

    public Product(Long id) {
        this.id = id;
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropertyLocation getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(PropertyLocation propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(propertyLocation, product.propertyLocation) && Objects.equals(category, product.category) && Objects.equals(name, product.name) && Objects.equals(productNumber, product.productNumber) && Objects.equals(link, product.link) && Objects.equals(inventory, product.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, propertyLocation, category, name, productNumber, link, inventory);
    }
}
