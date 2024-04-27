package org.app.api.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.app.api.dto.AvailableRestaurantsDTO;
import org.app.api.dto.RestaurantRestDTO;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.RestaurantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(RestaurantRestController.API_RESTAURANT)
public class RestaurantRestController {

    public static final String API_RESTAURANT = "/api/restaurant";
    public static final String AVAILABLE_RESTAURANTS = "/available_restaurants";

    private final RestaurantService restaurantService;
    private final RestaurantDTOMapper restaurantDTOMapper;

    @GetMapping(value = AVAILABLE_RESTAURANTS)
    public AvailableRestaurantsDTO availableRestaurantsDTO() {
        List<RestaurantRestDTO> list = restaurantService.findAvailableRestaurant().stream()
                .map(restaurantDTOMapper::mapToRestDTO)
                .toList();
        return restaurantDTOMapper.mapToRestDTOList(list);
    }
}
