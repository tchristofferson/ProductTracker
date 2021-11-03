package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.ApplicationSettings;
import com.tchristofferson.homeproducts.exc.InvalidIdException;
import com.tchristofferson.homeproducts.models.Property;
import com.tchristofferson.homeproducts.models.PropertyLocation;
import com.tchristofferson.homeproducts.services.PropertyLocationService;
import com.tchristofferson.homeproducts.services.PropertyService;
import com.tchristofferson.homeproducts.utils.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Controller
public class PropertyLocationController {

    private static final String BASE_URL = "/properties/{propertyId}";
    private final PropertyLocationService propertyLocationService;
    private final PropertyService propertyService;

    @Autowired
    public PropertyLocationController(PropertyLocationService propertyLocationService, PropertyService propertyService) {
        this.propertyLocationService = propertyLocationService;
        this.propertyService = propertyService;

        //TODO: Remove
        Property property = new Property();
        property.setId(1L);

        PropertyLocation propertyLocation = new PropertyLocation("Kitchen");
        propertyLocation.setProperty(property);
        propertyLocationService.savePropertyLocation(propertyLocation);
    }

    @GetMapping(BASE_URL)
    public String getPropertyLocations(Model model, @PathVariable("propertyId") Long propertyId) {
        return "redirect:/properties/" + propertyId + "/page/1";
    }

    @GetMapping(path = BASE_URL + "/page/{page}")
    public String getPropertyLocations(Model model, @PathVariable("propertyId") Long propertyId, @PathVariable("page") int page) {
        Optional<Property> optionalProperty = propertyService.getProperty(propertyId);

        if (optionalProperty.isEmpty())
            throw new InvalidIdException("Invalid property id!");

        Property property = optionalProperty.get();
        Page<PropertyLocation> propertyLocationPage = propertyLocationService.getPropertyLocations(PageRequest.of(page - 1, ApplicationSettings.ITEMS_PER_PAGE), propertyId);

        model.addAttribute("property", property);
        model.addAttribute("propertyLocations", propertyLocationPage);
        model.addAttribute("beginningUrl", BASE_URL + "/page");
        model.addAttribute("pageRange", BasicUtil.getNumberArray(propertyLocationPage));
        model.addAttribute("activePage", page);

        return "property-locations";
    }

    @GetMapping(path = BASE_URL + "/new")
    public String addPropertyLocation(Model model, @PathVariable("propertyId") long propertyId) {
        model.addAttribute("propertyId", propertyId);
        return "edit-property-location";
    }

    @GetMapping(path = BASE_URL + "/edit/{propertyLocationId}")
    public String editPropertyLocation(Model model, @PathVariable("propertyLocationId") long propertyLocationId) {
        Optional<PropertyLocation> optionalPropertyLocation = propertyLocationService.getPropertyLocation(propertyLocationId);

        if (optionalPropertyLocation.isEmpty())
            throw new InvalidIdException("Invalid property location id!");

        model.addAttribute("propertyId", optionalPropertyLocation.get().getProperty().getId());
        model.addAttribute("propertyLocation", optionalPropertyLocation.get());
        return "edit-property-location";
    }

    @PostMapping(path = BASE_URL + "/new")
    public String addPropertyLocation(@PathVariable("propertyId") long propertyId, @RequestParam("name") @NotBlank String name) {
        Property property = new Property();
        property.setId(propertyId);

        PropertyLocation propertyLocation = new PropertyLocation(name);
        propertyLocation.setProperty(property);

        try {
            propertyLocationService.savePropertyLocation(propertyLocation);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidIdException("Invalid property id!");
        }

        return "redirect:/properties/" + propertyId;
    }

    @PostMapping(path = BASE_URL + "/edit/{propertyLocationId}")
    public String editPropertyLocation(@PathVariable("propertyId") long propertyId, @PathVariable("propertyLocationId") long propertyLocationId, @RequestParam("name") @NotBlank String name) {
        Property property = new Property();
        property.setId(propertyId);

        PropertyLocation propertyLocation = new PropertyLocation(name);
        propertyLocation.setId(propertyLocationId);
        propertyLocation.setProperty(property);

        try {
            propertyLocationService.savePropertyLocation(propertyLocation);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidIdException("Invalid property or property location id!");
        }

        return "redirect:/properties/" + propertyId;
    }
}
