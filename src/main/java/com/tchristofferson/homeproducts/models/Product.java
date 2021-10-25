package com.tchristofferson.homeproducts.models;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Product")
@Table(name = "Products")
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(targetEntity = Location.class)
    @JoinColumn(name = "location_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_products_locationId"))
    private Long locationId;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_products_categoryId"))
    private Long categoryId;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "product_number", columnDefinition = "TEXT")
    private String productNumber;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Column(name = "inventory")
    private Integer inventory;

    public Product() {}

    public Product(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
        return id.equals(product.id) && Objects.equals(locationId, product.locationId) && Objects.equals(categoryId, product.categoryId)
                && Objects.equals(inventory, product.inventory) && name.equals(product.name)
                && Objects.equals(productNumber, product.productNumber) && Objects.equals(link, product.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationId, categoryId, name, productNumber, link, inventory);
    }
}
