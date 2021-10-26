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

    @ManyToOne
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "Fk_product_locationId"))
    private Location location;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "Fk_product_categoryId"))
    private Category category;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "product_number", columnDefinition = "TEXT")
    private String productNumber;

    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Column(name = "inventory")
    private Integer inventory;

    public Location getLocation() {
        return location;
    }

    public Category getCategory() {
        return category;
    }

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

    public void setLocation(Location location) {
        this.location = location;
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
        return Objects.equals(id, product.id) && Objects.equals(location, product.location) && Objects.equals(category, product.category) && Objects.equals(name, product.name) && Objects.equals(productNumber, product.productNumber) && Objects.equals(link, product.link) && Objects.equals(inventory, product.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, category, name, productNumber, link, inventory);
    }
}
