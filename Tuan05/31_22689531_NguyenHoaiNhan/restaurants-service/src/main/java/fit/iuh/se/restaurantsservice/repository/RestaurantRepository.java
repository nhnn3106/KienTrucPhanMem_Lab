package fit.iuh.se.restaurantsservice.repository;

import fit.iuh.se.restaurantsservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
