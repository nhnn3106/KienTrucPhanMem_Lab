package fit.iuh.se.restaurantsservice.controller;

import fit.iuh.se.restaurantsservice.entity.MenuItem;
import fit.iuh.se.restaurantsservice.service.RestaurantService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu-items")
@CrossOrigin(origins = "http://localhost:5173")
public class MenuItemController {

    private final RestaurantService restaurantService;

    public MenuItemController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItem(@PathVariable Long id) {
        return restaurantService.getMenuItem(id);
    }
}
