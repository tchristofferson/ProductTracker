package com.tchristofferson.homeproducts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tchristofferson.homeproducts.HomeProductsApplication;
import com.tchristofferson.homeproducts.exc.*;
import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HomeProductsApplication.class)
@SpringBootTest
public class ApplicationRestControllerTest {

    private static final String CATEGORIES_URI = "/categories";
    private static final String PROPERTIES_URI = "/properties";
    private static final String PROPERTY_LOCATIONS_URI = "/propertyLocations";

    private static final long EXISTING_PROP_ID = 1L;
    private static final long EXISTING_PROP_LOC_ID = 1L;
    private static final long EXISTING_CATEGORY_ID = 1L;

    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /*
     * POST Tests
     */

    /* Categories */

    @Test
    public void testCategoryPostWithNoName() throws Exception {
        performPost(CATEGORIES_URI, new Category())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testCategoryPostWithBlankName() throws Exception {
        performPost(CATEGORIES_URI, new Category(" "))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testCategoryPostWithId() throws Exception {
        performPost(CATEGORIES_URI, new Category(1L, "Lights"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), CategoryPostRequestIdException.class));
    }

    @Test
    public void testCategoryPost() throws Exception {
        final String categoryName = "Lights";

        performPost(CATEGORIES_URI, new Category(categoryName))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(categoryName));
    }

    /* Properties */

    @Test
    public void testPropertyPostWithNoName() throws Exception {
        performPost(PROPERTIES_URI, new Property())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testPropertyPostWithBlankName() throws Exception {
        performPost(PROPERTIES_URI, new Property(" "))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testPropertyPostWithId() throws Exception {
        performPost(PROPERTIES_URI, new Property(1L, "Home"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), PropertyPostRequestIdException.class));
    }

    @Test
    public void testPropertyPost() throws Exception {
        final String propertyName = "Home";

        performPost(PROPERTIES_URI, new Property(propertyName))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(propertyName));
    }

    /* Property Locations */

    @Test
    public void testPropertyLocationPostWithNoName() throws Exception {
        PropertyLocation propertyLocation = getPropertyLocation(null, null);

        //The 1 in the URI is the associated property id
        performPost(PROPERTY_LOCATIONS_URI, propertyLocation)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testPropertyLocationPostWithBlankName() throws Exception {
        PropertyLocation propertyLocation = getPropertyLocation(" ", null);

        performPost(PROPERTY_LOCATIONS_URI, propertyLocation)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testPropertyLocationPostWithId() throws Exception {
        PropertyLocation propertyLocation = getPropertyLocation("Kitchen", 1L);

        performPost(PROPERTY_LOCATIONS_URI, propertyLocation)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), PropertyLocationPostRequestIdException.class));
    }

    @Test
    public void testPropertyLocationPostWithNoProperty() throws Exception {
        PropertyLocation propertyLocation = new PropertyLocation("Kitchen");

        performPost(PROPERTY_LOCATIONS_URI, propertyLocation)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testPropertyLocationPostWithPropertyWithNoId() throws Exception {
        PropertyLocation propertyLocation = getPropertyLocation("Kitchen", null);
        //The property exists but doesn't have an id
        propertyLocation.getProperty().setId(null);

        performPost(PROPERTY_LOCATIONS_URI, propertyLocation)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), UnspecifiedPropertyIdException.class));
    }

    @Test
    public void testPropertyLocationPost() throws Exception {
        final String propertyLocationName = "Kitchen";
        PropertyLocation propertyLocation = getPropertyLocation(propertyLocationName, null);

        performPost("/propertyLocations", propertyLocation)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(propertyLocationName));
    }

    /* Products */

    @Test
    public void testProductPostWithNoName() throws Exception {
        Product product = getProduct(null, null, true);

        performPost("/products", product)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testProductPostWithBlankName() throws Exception {
        Product product = getProduct(" ", null, true);

        performPost("/products", product)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testProductPostWithId() throws Exception {
        Product product = getProduct("Light Bulb", 1L, true);

        performPost("/products", product)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), ProductPostRequestIdException.class));
    }

    @Test
    public void testProductPostWithNoPropertyLocation() throws Exception {
        Product product = getProduct("Light Bulb", null, true);
        product.setPropertyLocation(null);

        performPost("/products", product)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), MethodArgumentNotValidException.class));
    }

    @Test
    public void testProductPostWithPropertyLocationWithNoId() throws Exception {
        Product product = getProduct("Light Bulb", null, true);
        product.getPropertyLocation().setId(null);

        performPost("/products", product)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), UnspecifiedPropertyLocationIdException.class));
    }

    @Test
    public void testProductPostWithCategoryWithNoId() throws Exception {
        Product product = getProduct("Light Bulb", null, true);
        product.getCategory().setId(null);

        performPost("/products", product)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertEquals(result.getResolvedException().getClass(), UnspecifiedCategoryIdException.class));
    }

    @Test
    public void testProductPostWithNoCategory() throws Exception {
        final String productName = "Light Bulb";
        Product product = getProduct(productName, null, false);

        performPost("/products", product)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(productName))
                .andExpect(jsonPath("$.propertyLocation").exists())
                .andExpect(jsonPath("$.category").doesNotExist());
    }

    @Test
    public void testProductPost() throws Exception {
        final String productName = "Light Bulb";
        final String productNumber = UUID.randomUUID().toString();
        final String productLink = "http://buythings.com/light-bulbs";
        final int productInventory = 2;

        Product product = getProduct(productName, null, true);
        product.setProductNumber(productNumber);
        product.setLink(productLink);
        product.setInventory(productInventory);

        performPost("/products", product)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(productName))
                .andExpect(jsonPath("$.category").exists())
                .andExpect(jsonPath("$.propertyLocation").exists())
                .andExpect(jsonPath("$.productNumber").value(productNumber))
                .andExpect(jsonPath("$.link").value(productLink))
                .andExpect(jsonPath("$.inventory").value(productInventory));
    }

    private Product getProduct(String name, Long productId, boolean includeCategory) {
        Product product = new Product(productId, name);
        product.setPropertyLocation(new PropertyLocation(EXISTING_PROP_LOC_ID));

        if (includeCategory)
            product.setCategory(new Category(EXISTING_CATEGORY_ID));

        return product;
    }

    private PropertyLocation getPropertyLocation(String name, Long propertyLocationId) {
        PropertyLocation propertyLocation = new PropertyLocation(propertyLocationId, name);
        propertyLocation.setProperty(new Property(EXISTING_PROP_ID));

        return propertyLocation;
    }

    private ResultActions performGet(String uri) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions performGet(String uri, String paramName, String param) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).param(paramName, param));
    }

    private ResultActions performPost(String uri, Object obj) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(obj))
                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(result -> logger.info(result.getResponse().getContentAsString()));//Log error message or returned json
    }
}