package fit.iuh.se.restaurantsservice.repository;

import fit.iuh.se.restaurantsservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
