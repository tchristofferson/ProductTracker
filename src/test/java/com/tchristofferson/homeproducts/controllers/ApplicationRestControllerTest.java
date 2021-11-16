package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.HomeProductsApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HomeProductsApplication.class)
@SpringBootTest
public class ApplicationRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getCategories() throws Exception {
        performGet("/categories")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void getCategory() throws Exception {
        final int id = 1;

        performGet("/categories/" + id)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    public void getProperties() throws Exception {
        performGet("/properties")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void getProperty() throws Exception {
        final int id = 1;

        performGet("/properties/" + id)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    public void getPropertyLocations() throws Exception {
        final int propertyId = 1;

        performGet("/propertyLocations", "propertyId", String.valueOf(propertyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void getPropertyLocation() throws Exception {
        final int propertyLocationId = 1;

        performGet("/propertyLocations/" + propertyLocationId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(propertyLocationId))
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    public void getProducts() {
    }

    @Test
    public void getProduct() {
    }

    @Test
    public void addCategory() {
    }

    @Test
    public void addProperty() {
    }

    @Test
    public void addPropertyLocation() {
    }

    @Test
    public void addProduct() {
    }

    @Test
    public void updateCategory() {
    }

    @Test
    public void updateProperty() {
    }

    @Test
    public void updatePropertyLocation() {
    }

    @Test
    public void updateProduct() {
    }

    @Test
    public void deleteCategory() {
    }

    @Test
    public void deleteProperty() {
    }

    @Test
    public void deletePropertyLocation() {
    }

    @Test
    public void deleteProduct() {
    }

    private ResultActions performGet(String uri) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions performGet(String uri, String paramName, String param) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).param(paramName, param));
    }
}