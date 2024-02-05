package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.app.api.dto.*;
import org.app.api.dto.mapper.*;
import org.app.bussiness.CustomerService;
import org.app.bussiness.FoodOrderService;
import org.app.bussiness.RestaurantService;
import org.app.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Data
@Controller
@AllArgsConstructor
public class FoodOrderController {

    public static final String CREATE_FOOD_ORDER = "/create_food_order";
    public static final String SUBMIT_ORDER = "/submit_order";
    public static final String ADD_APPETIZER = "/add_appetizer";
    public static final String ADD_SOUP = "/add_soup";
    public static final String ADD_MAIN_MEAL = "/add_main_meal";
    public static final String ADD_DESERT = "/add_desert";
    public static final String ADD_DRINK = "/add_drink";

    private FoodOrderService foodOrderService;
    private RestaurantService restaurantService;
    private CustomerService customerService;
    private AppetizerMapperDTO appetizerMapperDTO;
    private SoupMapperDTO soupMapperDTO;
    private MainMealMapperDTO mainMealMapperDTO;
    private DesertMapperDTO desertMapperDTO;
    private DrinkMapperDTO drinkMapperDTO;
    private MenuPosition menuPosition;

    private static Set<Appetizer> appetizerSet;
    private static Set<Soup> soupSet;
    private static Set<MainMeal> mainMealSet;
    private static Set<Desert> desertSet;
    private static Set<Drink> drinkSet;


    @GetMapping(value = CREATE_FOOD_ORDER)
    public ModelAndView createFoodOrderPage(Model model) {
        model.addAttribute("appetizerDTO", new AppetizerDTO());
        model.addAttribute("soupDTO", new SoupDTO());
        model.addAttribute("mainMealDTO", new MainMealDTO());
        model.addAttribute("desertDTO", new DesertDTO());
        model.addAttribute("drinkDTO", new DrinkDTO());
        Map<String, ?> addedDishes = prepareAddedDishesAndSumCost();
        Map<String, ?> availableMenuPosition = menuPosition
                .prepareMenuPositions(ChooseRestaurantToFoodOrderController.uniqueCodeRestaurantToOrderFood);


        return new ModelAndView("create_food_order", addedDishes);
    }

    @PostMapping(value = SUBMIT_ORDER)
    public String submitOrder() {
        foodOrderService.saveNewFoodOrder(FoodOrder.builder()
                .orderNumber(UUID.randomUUID().toString())
                .receivedDateTime(OffsetDateTime.now(ZoneOffset.UTC))
                .sumCost(BigDecimal.ZERO)
                .restaurant(restaurantService.findByUniqueCode(
                        ChooseRestaurantToFoodOrderController.uniqueCodeRestaurantToOrderFood
                ))
                .customer(customerService.findCustomerByEmail(CustomerController.emailCustomer))
                .appetizers(appetizerSet)
                .soups(soupSet)
                .mainMeals(mainMealSet)
                .deserts(desertSet)
                .drinks(drinkSet)
                .build());
        return "submit_order_success";
    }

    @PostMapping(value = ADD_APPETIZER)
    public String addAppetizer(
            @Valid @ModelAttribute("appetizerDTO") AppetizerDTO appetizerDTO
    ) {
        appetizerSet.add(appetizerMapperDTO.mapFromDTO(appetizerDTO));

        return "redirect:/create_food_order";
    }

    @PostMapping(value = ADD_SOUP)
    public String addSoup(
            @Valid @ModelAttribute("soupDTO") SoupDTO soupDTO
    ) {
        soupSet.add(soupMapperDTO.mapFromDTO(soupDTO));

        return "redirect:/create_food_order";
    }

    @PostMapping(value = ADD_MAIN_MEAL)
    public String addMainMeal(
            @Valid @ModelAttribute("mainMealDTO") MainMealDTO mainMealDTO
    ) {
        mainMealSet.add(mainMealMapperDTO.mapFromDTO(mainMealDTO));

        return "redirect:/create_food_order";
    }

    @PostMapping(value = ADD_DESERT)
    public String addDesert(
            @Valid @ModelAttribute("desertDTO") DesertDTO desertDTO
    ) {
        desertSet.add(desertMapperDTO.mapFromDTO(desertDTO));

        return "redirect:/create_food_order";
    }

    @PostMapping(value = ADD_DRINK)
    public String addDrink(
            @Valid @ModelAttribute("drinkDTO") DrinkDTO drinkDTO
    ) {
        drinkSet.add(drinkMapperDTO.mapFromDTO(drinkDTO));

        return "redirect:/create_food_order";
    }


    private Map<String, ?> prepareAddedDishesAndSumCost() {
        return Map.of(
                "appetizerDTOsAdded", appetizerSet,
                "soupDTOsAdded", soupSet,
                "mainMealDTOsAdded", mainMealSet,
                "desertDTOsAdded", desertSet,
                "drinkDTOsAdded", drinkSet,
                "sumCost", foodOrderService.calculateCost(FoodOrder.builder()
                        .appetizers(appetizerSet)
                        .soups(soupSet)
                        .mainMeals(mainMealSet)
                        .deserts(desertSet)
                        .drinks(drinkSet)
                        .build())
        );
    }


}
