package com.tchristofferson.homeproducts.controllers;

import com.tchristofferson.homeproducts.exc.InvalidIdException;
import com.tchristofferson.homeproducts.models.Category;
import com.tchristofferson.homeproducts.models.Location;
import com.tchristofferson.homeproducts.models.Product;
import com.tchristofferson.homeproducts.services.CategoryService;
import com.tchristofferson.homeproducts.services.LocationService;
import com.tchristofferson.homeproducts.services.ProductService;
import com.tchristofferson.homeproducts.utils.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/locations")
public class ProductLocationController {

    private static final int ITEMS_PER_PAGE = 24;
    private final LocationService locationService;
    private final ProductService productService;

    @Autowired
    public ProductLocationController(LocationService locationService, ProductService productService) {
        this.locationService = locationService;
        this.productService = productService;
        locationService.saveLocation(new Location("Home"));
    }

    /* GETs */

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

    @GetMapping(path = "/{id}")
    public String getLocation(Model model, @PathVariable("id") long locationId) {
        Optional<Location> optionalLocation = locationService.getLocation(locationId);

        if (optionalLocation.isEmpty())
            throw new InvalidIdException("Invalid location id!");

        Map<Category, List<Product>> categoryProductMap = productService.getCategoryProductMap(optionalLocation.get());

        model.addAttribute("location", optionalLocation.get());
        model.addAttribute("categoryProductMap", categoryProductMap);
        return "location";
    }

    /* POSTs */

    @PostMapping(path = "/new")
    public String addLocation(@RequestParam("name") @NotBlank String name) {
        locationService.saveLocation(new Location(name));
        return "redirect:/locations/page/1";
    }
}
