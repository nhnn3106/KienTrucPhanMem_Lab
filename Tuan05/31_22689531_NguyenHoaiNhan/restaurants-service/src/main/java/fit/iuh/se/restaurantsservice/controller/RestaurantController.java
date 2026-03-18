package fit.iuh.se.restaurantsservice.controller;

import fit.iuh.se.restaurantsservice.dto.CreateMenuItemRequest;
import fit.iuh.se.restaurantsservice.dto.CreateRestaurantRequest;
import fit.iuh.se.restaurantsservice.entity.MenuItem;
import fit.iuh.se.restaurantsservice.entity.Restaurant;
import fit.iuh.se.restaurantsservice.service.RestaurantService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:5173")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
        return restaurantService.createRestaurant(request);
    }

    @PostMapping("/{restaurantId}/menu-items")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem addMenuItem(@PathVariable Long restaurantId,
                                @Valid @RequestBody CreateMenuItemRequest request) {
        return restaurantService.addMenuItem(restaurantId, request);
    }

    @GetMapping
    public List<Restaurant> getRestaurants() {
        return restaurantService.getRestaurants();
    }
}
