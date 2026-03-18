package fit.iuh.se.oderservice.repository;

import fit.iuh.se.oderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
