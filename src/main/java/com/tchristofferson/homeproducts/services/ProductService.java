package com.tchristofferson.homeproducts.services;

import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Location;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Map<Category, List<Product>> getCategoryProductMap(Location location) {
        return productRepository.findByLocationOrderByCategoryAsc(location).stream()
                .collect(Collectors.groupingBy(Product::getCategory, LinkedHashMap::new, Collectors.toList()));
    }
}
