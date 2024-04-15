package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.controller.dataForController.MenuPosition;
import org.app.api.dto.*;
import org.app.bussiness.*;
import org.app.domain.FoodOrder;
import org.app.domain.StreetDelivery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.app.api.controller.CustomerController.*;

@Controller
@AllArgsConstructor
public class ChooseRestaurantToFoodOrderController {

    public static final String CHOOSE_STREET_DELIVERY = "/choose_street_delivery";
    public static final String ENTER_STREET_AND_CITY = "/enter_street_and_city";
    public static final String CHOOSE_RESTAURANT_TO_ORDER_FOOD = "/choose_restaurant_to_order_food";
    public static final String SHOW_OPINION = "/show_opinion";

    private StreetDeliveryService streetDeliveryService;
    private RestaurantService restaurantService;
    private MenuPosition menuPosition;
    private OpinionService opinionService;

    private static StreetDelivery streetDelivery;
    protected static String uniqueCodeRestaurantToOrderFood;

    @GetMapping(value = CUSTOMER + CHOOSE_STREET_DELIVERY)
    public ModelAndView chooseRestaurantPage(Model model) {
        Map<String, ?> availableAvailableRestaurant = prepareAvailableRestaurant(streetDelivery);
        model.addAttribute("chooseStreetDeliveryDTO", new ChooseStreetDeliveryDTO());
        model.addAttribute("variableDTO", new VariableDTO());
        return new ModelAndView("choose_restaurant", availableAvailableRestaurant);
    }

    @PostMapping(value = CUSTOMER + ENTER_STREET_AND_CITY)
    public String chooseStreetDelivery(
            @Valid @ModelAttribute("chooseStreetDeliveryDTO") ChooseStreetDeliveryDTO chooseStreetDeliveryDTO
    ) {
        streetDelivery = streetDeliveryService.findByStreetAndCity(
                chooseStreetDeliveryDTO.getChooseStreet(),
                chooseStreetDeliveryDTO.getChooseCity()
        );
        return "redirect:/customer/choose_street_delivery";
    }

    @PostMapping(value = CUSTOMER + CHOOSE_RESTAURANT_TO_ORDER_FOOD)
    public ModelAndView chooseRestaurant(
            Model model,
            @Valid @ModelAttribute("variableDTO")VariableDTO variableDTO
            ) {

        uniqueCodeRestaurantToOrderFood = variableDTO.getUniqueCode();

        model.addAttribute("appetizerDTO", new AppetizerDTO());
        model.addAttribute("soupDTO", new SoupDTO());
        model.addAttribute("mainMealDTO", new MainMealDTO());
        model.addAttribute("desertDTO", new DesertDTO());
        model.addAttribute("drinkDTO", new DrinkDTO());

        Map<String, ?> menuPositions = menuPosition.prepareMenuPositions(uniqueCodeRestaurantToOrderFood);

        return new ModelAndView("create_food_order", menuPositions);
    }

    @PostMapping(value = CUSTOMER + SHOW_OPINION)
    public ModelAndView showOpinionRestaurant(
            Model model,
            @Valid @ModelAttribute("variableDTO")VariableDTO variableDTO
            ) {

        uniqueCodeRestaurantToOrderFood = variableDTO.getUniqueCode();

        Map<String, ?> opinions = prepareOpinionsRestaurant(uniqueCodeRestaurantToOrderFood);

        return new ModelAndView("show_opinion_restaurant", opinions);
    }

    protected Map<String, ?> prepareOpinionsRestaurant(String uniqueCodeRestaurantToOrderFood) {
        return Map.of(
                "opinionRestaurant", opinionService.findOpinionByRestaurant(uniqueCodeRestaurantToOrderFood)
        );
    }

    protected Map<String, ?> prepareAvailableRestaurant(StreetDelivery streetDelivery) {
        Map<String, ?> mapRestaurant = new HashMap<>();
        if(streetDelivery == null) {
            return Map.of();
        } else {
            return Map.of(
                    "availableRestaurantDTOs",
                    restaurantService.findAvailableRestaurantByStreetDelivery(streetDelivery)
            );
        }

    }
}
