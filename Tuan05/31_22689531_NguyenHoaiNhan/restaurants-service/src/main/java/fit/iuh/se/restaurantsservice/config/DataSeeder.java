package fit.iuh.se.restaurantsservice.config;

import fit.iuh.se.restaurantsservice.entity.MenuItem;
import fit.iuh.se.restaurantsservice.entity.Restaurant;
import fit.iuh.se.restaurantsservice.repository.MenuItemRepository;
import fit.iuh.se.restaurantsservice.repository.RestaurantRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedRestaurants(RestaurantRepository restaurantRepository,
                                             MenuItemRepository menuItemRepository) {
        return args -> {
            if (restaurantRepository.count() > 0) {
                return;
            }

            Restaurant bento = new Restaurant();
            bento.setName("Bento Station");
            bento.setAddress("12 Nguyen Hue, Quan 1");

            Restaurant pho = new Restaurant();
            pho.setName("Pho Saigon");
            pho.setAddress("88 Hai Ba Trung, Quan 3");

            restaurantRepository.saveAll(List.of(bento, pho));

            MenuItem item1 = new MenuItem();
            item1.setName("Bento ga nuong");
            item1.setPrice(new BigDecimal("59000"));
            item1.setRestaurant(bento);

            MenuItem item2 = new MenuItem();
            item2.setName("Bento bo sot tieu");
            item2.setPrice(new BigDecimal("69000"));
            item2.setRestaurant(bento);

            MenuItem item3 = new MenuItem();
            item3.setName("Pho tai nam");
            item3.setPrice(new BigDecimal("55000"));
            item3.setRestaurant(pho);

            MenuItem item4 = new MenuItem();
            item4.setName("Pho bo vien");
            item4.setPrice(new BigDecimal("50000"));
            item4.setRestaurant(pho);

            menuItemRepository.saveAll(List.of(item1, item2, item3, item4));
        };
    }
}
