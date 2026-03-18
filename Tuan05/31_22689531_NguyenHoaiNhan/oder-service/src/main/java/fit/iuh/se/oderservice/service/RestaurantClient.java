package fit.iuh.se.oderservice.service;

import fit.iuh.se.oderservice.dto.MenuItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestaurantClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public RestaurantClient(RestTemplate restTemplate,
                            @Value("${restaurants.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public MenuItemDto fetchMenuItem(Long menuItemId) {
        String url = String.format("%s/api/menu-items/%d", baseUrl, menuItemId);
        ResponseEntity<MenuItemDto> response;
        try {
            response = restTemplate.getForEntity(url, MenuItemDto.class);
        } catch (HttpClientErrorException ex) {
            return null;
        }
        return response.getBody();
    }
}
