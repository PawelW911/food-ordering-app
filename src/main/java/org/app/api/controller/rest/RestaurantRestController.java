package org.app.api.controller.rest;

import lombok.AllArgsConstructor;
import org.app.api.dto.AvailableRestaurantsDTO;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(RestaurantRestController.API_RESTAURANT)
public class RestaurantRestController {

    public static final String API_RESTAURANT = "/api/restaurant";
    public static final String AVAILABLE_RESTAURANT = "/available_restaurant";

    private final RestaurantService restaurantService;
    private final RestaurantDTOMapper restaurantDTOMapper;

    @GetMapping(AVAILABLE_RESTAURANT)
    public AvailableRestaurantsDTO availableRestaurantsDTO() {
        return getAvailableRestaurants();
    }

    private AvailableRestaurantsDTO getAvailableRestaurants() {
        return AvailableRestaurantsDTO.builder()
                .availableRestaurants(restaurantService.findAvailableRestaurant().stream()
                        .map(restaurantDTOMapper::mapToRestDTO)
                        .toList())
                .build();
    }
}
