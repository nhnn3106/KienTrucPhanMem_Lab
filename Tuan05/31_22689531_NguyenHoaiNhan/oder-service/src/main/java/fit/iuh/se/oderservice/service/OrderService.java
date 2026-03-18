package fit.iuh.se.oderservice.service;

import fit.iuh.se.oderservice.dto.CreateOrderItemRequest;
import fit.iuh.se.oderservice.dto.CreateOrderRequest;
import fit.iuh.se.oderservice.dto.MenuItemDto;
import fit.iuh.se.oderservice.entity.Order;
import fit.iuh.se.oderservice.entity.OrderItem;
import fit.iuh.se.oderservice.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantClient restaurantClient;

    public OrderService(OrderRepository orderRepository, RestaurantClient restaurantClient) {
        this.orderRepository = orderRepository;
        this.restaurantClient = restaurantClient;
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());

        BigDecimal total = BigDecimal.ZERO;
        for (CreateOrderItemRequest itemRequest : request.getItems()) {
            MenuItemDto menuItem = restaurantClient.fetchMenuItem(itemRequest.getMenuItemId());
            if (menuItem == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Menu item not found: " + itemRequest.getMenuItemId());
            }
            OrderItem item = new OrderItem();
            item.setMenuItemId(menuItem.getId());
            item.setMenuItemName(menuItem.getName());
            item.setUnitPrice(menuItem.getPrice());
            item.setQuantity(itemRequest.getQuantity());

            BigDecimal lineTotal = menuItem.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            item.setLineTotal(lineTotal);
            item.setOrder(order);

            order.getItems().add(item);
            total = total.add(lineTotal);
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }
}
