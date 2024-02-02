package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.ChooseStreetDeliveryDTO;
import org.app.api.dto.VariableDTO;
import org.app.bussiness.StreetDeliveryService;
import org.app.domain.StreetDelivery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ChooseRestaurantToFoodOrderController {

    public static final String CREATE_ORDER_FOOD = "/choose_street_delivery";
    public static final String ENTER_STREET_AND_CITY = "/enter_street_and_city";
    public static final String CHOOSE_RESTAURANT_TO_ORDER_FOOD = "/choose_restaurant_to_order_food";

    StreetDeliveryService streetDeliveryService;

    private static StreetDelivery streetDelivery;
    private static String uniqueCodeRestaurantToOrderFood;

    @GetMapping(value = CREATE_ORDER_FOOD)
    public ModelAndView chooseRestaurantPage(Model model) {
        Map<String, ?> availableAvailableRestaurant = prepareAvailableRestaurant(streetDelivery);
        return new ModelAndView("choose_restaurant", availableAvailableRestaurant);
    }

    @PostMapping(value = ENTER_STREET_AND_CITY)
    public String chooseStreetDelivery(
            @Valid @ModelAttribute("chooseStreetDeliveryDTO") ChooseStreetDeliveryDTO chooseStreetDeliveryDTO
    ) {
        streetDelivery = streetDeliveryService.findByStreetAndCity(
                chooseStreetDeliveryDTO.getChooseStreet(),
                chooseStreetDeliveryDTO.getChooseCity()
        );
        return "redirect:/choose_street_delivery";
    }

    @PostMapping(value = CHOOSE_RESTAURANT_TO_ORDER_FOOD)
    public String chooseRestaurant(
            @Valid @ModelAttribute("variableDTO")VariableDTO variableDTO
            ) {
        uniqueCodeRestaurantToOrderFood = variableDTO.getUniqueCode();

        return "create_food_order";
    }

    private Map<String, ?> prepareAvailableRestaurant(StreetDelivery streetDelivery) {
        Map<String, ?> mapRestaurant = new HashMap<>();
        if(streetDelivery != null) {
            return Map.of("availableRestaurantDTOs", streetDelivery.getRestaurant());
        } else {
            return Map.of();
        }

    }
}
