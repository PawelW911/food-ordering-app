package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.OwnerService;
import org.app.bussiness.RestaurantService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Data
@AllArgsConstructor
public class RestaurantController {

    public static final String RESTAURANT = "/restaurant";
    public static final String ADD_NEW_RESTAURANT = "/add_new_restaurant";

    private RestaurantService restaurantService;
    private RestaurantDTOMapper restaurantDTOMapper;
    private OwnerService ownerService;

    @GetMapping(value = RESTAURANT + ADD_NEW_RESTAURANT)
    public String ownerPage() {
        return "add_new_restaurant";
    }

    @PostMapping(value = RESTAURANT + ADD_NEW_RESTAURANT)
    public String makePurchase(
            @Valid @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO,
            ModelMap model
    ) {




        return "car_purchase_done";
    }
}
