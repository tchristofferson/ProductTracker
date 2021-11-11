package com.tchristofferson.homeproducts.services;

import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProduct(long productId) {
        return productRepository.findById(productId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProducts(long propertyLocationId) {
        return productRepository.findByPropertyLocationOrderByCategoryAsc(new PropertyLocation(propertyLocationId));
    }

    public Map<Category, List<Product>> getCategoryProductMap(long propertyLocationId) {
        PropertyLocation propertyLocation = new PropertyLocation(propertyLocationId);

        return productRepository.findByPropertyLocationOrderByCategoryAsc(propertyLocation).stream()
                .collect(Collectors.groupingBy(Product::getCategory, LinkedHashMap::new, Collectors.toList()));
    }
}
