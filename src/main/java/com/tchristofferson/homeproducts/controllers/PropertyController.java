package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.exc.InvalidIdException;
import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.services.PropertyService;
import com.tchristofferson.homeproducts.utils.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private static final int ITEMS_PER_PAGE = 24;
    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;

        //TODO: Remove
        propertyService.saveProperty(new Property("Home"));
    }

    @GetMapping
    public String getProperties() {
        return "redirect:/properties/page/1";
    }

    @GetMapping(path = "/page/{page}")
    public String getProperties(Model model, @PathVariable("page") int page) {
        Page<Property> propertyPage = propertyService.getPropertyPage(PageRequest.of(page - 1, ITEMS_PER_PAGE));

        model.addAttribute("properties", propertyPage);
        model.addAttribute("beginningUrl", "/properties");
        model.addAttribute("pageRange", BasicUtil.getNumberArray(propertyPage));
        model.addAttribute("activePage", page);

        return "properties";
    }

    @GetMapping(path = "/new")
    public String addProperty() {
        return "edit-property";
    }

    @GetMapping(path = "/edit/{propertyId}")
    public String editProperty(Model model, @PathVariable("propertyId") long propertyId) {
        Optional<Property> optionalProperty = propertyService.getProperty(propertyId);

        if (optionalProperty.isEmpty())
            throw new InvalidIdException("Invalid property id!");

        model.addAttribute("property", optionalProperty.get());
        return "edit-property";
    }

    @PostMapping(path = "/new")
    public String addProperty(@RequestParam("propertyName") @NotBlank String name) {
        propertyService.saveProperty(new Property(name));
        return "redirect:/properties";
    }

    @PostMapping(path = "/edit")
    public String editProperty(@RequestParam("id") long propertyId, @RequestParam("propertyName") String name) {
        Property property = new Property(name);
        property.setId(propertyId);

        try {
            propertyService.saveProperty(property);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidIdException("Invalid property id!");
        }

        return "redirect:/properties";
    }

    @DeleteMapping(path = "/{id}")
    public String deleteProperty(@PathVariable("id") Long id) {
        propertyService.deleteProperty(id);
        return "redirect:/properties";
    }
}
