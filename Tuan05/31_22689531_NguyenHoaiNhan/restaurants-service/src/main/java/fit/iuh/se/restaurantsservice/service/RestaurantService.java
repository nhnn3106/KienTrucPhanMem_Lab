package fit.iuh.se.restaurantsservice.service;

import fit.iuh.se.restaurantsservice.dto.CreateMenuItemRequest;
import fit.iuh.se.restaurantsservice.dto.CreateRestaurantRequest;
import fit.iuh.se.restaurantsservice.entity.MenuItem;
import fit.iuh.se.restaurantsservice.entity.Restaurant;
import fit.iuh.se.restaurantsservice.repository.MenuItemRepository;
import fit.iuh.se.restaurantsservice.repository.RestaurantRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Restaurant createRestaurant(CreateRestaurantRequest request) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        return restaurantRepository.save(restaurant);
    }

    public MenuItem addMenuItem(Long restaurantId, CreateMenuItemRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setPrice(request.getPrice());
        menuItem.setRestaurant(restaurant);

        MenuItem saved = menuItemRepository.save(menuItem);
        restaurant.getMenuItems().add(saved);
        return saved;
    }

    public MenuItem getMenuItem(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found"));
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }
}
