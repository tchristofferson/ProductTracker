package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.models.Location;
import com.tchristofferson.homeproducts.services.LocationService;
import com.tchristofferson.homeproducts.utils.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private static final int ITEMS_PER_PAGE = 24;
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
        locationService.saveLocation(new Location("Home"));
    }

    @GetMapping
    public String getLocations() {
        return "redirect:/locations/page/1";
    }

    @GetMapping(path = "/page/{page}")
    public String getLocations(Model model, @PathVariable("page") int page) {
        Page<Location> locationPage = locationService.getLocationPage(PageRequest.of(page - 1, ITEMS_PER_PAGE));
        model.addAttribute("locations", locationPage);
        model.addAttribute("beginningUrl", "/locations/page");
        model.addAttribute("activePage", page);
        model.addAttribute("pageRange", BasicUtil.getNumberArray(locationPage));

        return "locations";
    }

    @PostMapping(path = "/new")
    public String addLocation(@RequestParam("name") @NotBlank String name) {
        locationService.saveLocation(new Location(name));
        return "redirect:/locations/page/1";
    }
}
