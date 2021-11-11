package com.tchristofferson.homeproducts;

import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.services.CategoryService;
import com.tchristofferson.homeproducts.services.ProductService;
import com.tchristofferson.homeproducts.services.PropertyLocationService;
import com.tchristofferson.homeproducts.services.PropertyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomeProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeProductsApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CategoryService categoryService, PropertyService propertyService, PropertyLocationService propertyLocationService, ProductService productService) {
		return args -> {
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
		};
	}

}
