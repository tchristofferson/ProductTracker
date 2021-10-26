package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts() {
        return "redirect:/locations";
    }

    @PostMapping(path = "/new")
    public String addProduct(
            @RequestParam("location") int locationId,
            @RequestParam("category") @Nullable Integer categoryId,
            @RequestParam("name") String name,
            @RequestParam("productNumber") @Nullable String productNumber,
            @RequestParam("link") @Nullable String link,
            @RequestParam("inventory") @Nullable Integer inventory) {
        //TODO: Implement addProduct post mapping
        return null;
    }
}
