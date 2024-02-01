package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.ChooseStreetDeliveryDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class FoodOrderController {

    public static final String CREATE_ORDER_FOOD = "/create_order_food";
    public static final String ENTER_STREET_AND_CITY = "/enter_street_and_city";

    @GetMapping(value = CREATE_ORDER_FOOD)
    public String chooseRestaurantPage(Model model) {
        return "choose_restaurant";
    }

    @PostMapping(value = ENTER_STREET_AND_CITY)
    public String chooseStreetDelivery(
            @Valid @ModelAttribute("chooseStreetDeliveryDTO") ChooseStreetDeliveryDTO chooseStreetDeliveryDTO
    ) {

        return "redirect:/create_order_food";
    }
}
