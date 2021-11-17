package com.tchristofferson.homeproducts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tchristofferson.homeproducts.HomeProductsApplication;
import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Property;
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
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HomeProductsApplication.class)
@SpringBootTest
public class ApplicationRestControllerTest {

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
        performPost("/categories", new Category())
                .andExpect(status().is4xxClientError())
                .andDo(result -> logger.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void testCategoryPostWithBlankName() throws Exception {
        performPost("/categories", new Category(" "))
                .andExpect(status().is4xxClientError())
                .andDo(result -> logger.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void testCategoryPostWithId() throws Exception {
        performPost("/categories", new Category(1L, "Lights"))
                .andExpect(status().is4xxClientError())
                .andDo(result -> logger.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void testCategoryPost() throws Exception {
        final String categoryName = "Lights";

        performPost("/categories", new Category(categoryName))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(categoryName));
    }

    /* Properties */

    @Test
    public void testPropertyPostWithNoName() throws Exception {
        performPost("/properties", new Property())
                .andExpect(status().is4xxClientError())
                .andDo(result -> logger.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void testPropertyPostWithBlankName() throws Exception {
        performPost("/properties", new Property(" "))
                .andExpect(status().is4xxClientError())
                .andDo(result -> System.out.println(result.getResponse().getErrorMessage()))
                .andDo(result -> logger.info(result.getResponse().getContentAsString()));
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
                .characterEncoding(StandardCharsets.UTF_8));
    }
}