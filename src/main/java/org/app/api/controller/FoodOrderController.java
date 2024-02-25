package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.api.controller.dataForController.MenuPosition;
import org.app.api.dto.*;
import org.app.api.dto.mapper.*;
import org.app.bussiness.*;
import org.app.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class FoodOrderController {

    public static final String CREATE_FOOD_ORDER = "/create_food_order";
    public static final String SUBMIT_ORDER = "/submit_order";
    public static final String ADD_APPETIZER_TO_ORDER = "/add_appetizer_to_order";
    public static final String ADD_SOUP_TO_ORDER = "/add_soup_to_order";
    public static final String ADD_MAIN_MEAL_TO_ORDER = "/add_main_meal_to_order";
    public static final String ADD_DESERT_TO_ORDER = "/add_desert_to_order";
    public static final String ADD_DRINK_TO_ORDER = "/add_drink_to_order";

    private FoodOrderService foodOrderService;
    private RestaurantService restaurantService;
    private CustomerService customerService;
    private AppetizerMapperDTO appetizerMapperDTO;
    private SoupMapperDTO soupMapperDTO;
    private MainMealMapperDTO mainMealMapperDTO;
    private DesertMapperDTO desertMapperDTO;
    private DrinkMapperDTO drinkMapperDTO;
    private MenuPosition menuPosition;
    private AppetizerService appetizerService;
    private SoupService soupService;
    private MainMealService mainMealService;
    private DesertService desertService;
    private DrinkService drinkService;

    private static Set<Appetizer> appetizerSet = new HashSet<>();
    private static Set<Soup> soupSet = new HashSet<>();
    private static Set<MainMeal> mainMealSet = new HashSet<>();
    private static Set<Desert> desertSet = new HashSet<>();
    private static Set<Drink> drinkSet = new HashSet<>();


    @GetMapping(value = CREATE_FOOD_ORDER)
    public ModelAndView createFoodOrderPage(Model model) {
        model.addAttribute("appetizerDTO", new AppetizerDTO());
        model.addAttribute("soupDTO", new SoupDTO());
        model.addAttribute("mainMealDTO", new MainMealDTO());
        model.addAttribute("desertDTO", new DesertDTO());
        model.addAttribute("drinkDTO", new DrinkDTO());
        Map<String, ?> addedDishesAndAvailablePositionMenu = prepareAddedDishesAndSumCost();

        model.addAttribute("sumCost", foodOrderService.calculateCost(FoodOrder.builder()
                .appetizers(appetizerSet)
                .soups(soupSet)
                .mainMeals(mainMealSet)
                .deserts(desertSet)
                .drinks(drinkSet)
                .build()));

        return new ModelAndView("create_food_order", addedDishesAndAvailablePositionMenu);
    }

    @PostMapping(value = SUBMIT_ORDER)
    public String submitOrder() {
        foodOrderService.saveNewFoodOrder(FoodOrder.builder()
                .foodOrderNumber(UUID.randomUUID().toString())
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

    @PostMapping(value = ADD_APPETIZER_TO_ORDER)
    public String addAppetizer(
            @Valid @ModelAttribute("appetizerDTO") AppetizerDTO appetizerDTO
    ) {
        Appetizer appetizerFound = appetizerService.findById(appetizerDTO.getAppetizerId());

        if(appetizerDTO.getQuantity()>0) {
            appetizerSet.add(appetizerFound.withAppetizerId(null).withQuantity(appetizerDTO.getQuantity()));
        }
        return "redirect:/create_food_order";
    }

    @PostMapping(value = ADD_SOUP_TO_ORDER)
    public String addSoup(
            @Valid @ModelAttribute("soupDTO") SoupDTO soupDTO
    ) {
        Soup soupFound = soupService.findById(soupDTO.getSoupId());

        if(soupDTO.getQuantity()>0) {
            soupSet.add(soupFound.withSoupId(null).withQuantity(soupDTO.getQuantity()));
        }
        return "redirect:/create_food_order";
    }

    @PostMapping(value = ADD_MAIN_MEAL_TO_ORDER)
    public String addMainMeal(
            @Valid @ModelAttribute("mainMealDTO") MainMealDTO mainMealDTO
    ) {
        MainMeal mainMealFound = mainMealService.findById(mainMealDTO.getMainMealId());

        if(mainMealDTO.getQuantity()>0) {
            mainMealSet.add(mainMealFound.withMainMealId(null).withQuantity(mainMealDTO.getQuantity()));
        }
        return "redirect:/create_food_order";
    }

    @PostMapping(value = ADD_DESERT_TO_ORDER)
    public String addDesert(
            @Valid @ModelAttribute("desertDTO") DesertDTO desertDTO
    ) {
        Desert desertFound = desertService.findById(desertDTO.getDesertId());

        if(desertDTO.getQuantity()>0) {
            desertSet.add(desertFound.withDesertId(null).withQuantity(desertDTO.getQuantity()));
        }
        return "redirect:/create_food_order";
    }

    @PostMapping(value = ADD_DRINK_TO_ORDER)
    public String addDrink(
            @Valid @ModelAttribute("drinkDTO") DrinkDTO drinkDTO
    ) {
        Drink drinkFound = drinkService.findById(drinkDTO.getDrinkId());

        if(drinkDTO.getQuantity()>0) {
            drinkSet.add(drinkFound.withDrinkId(null).withQuantity(drinkDTO.getQuantity()));
        }
        return "redirect:/create_food_order";
    }


    private Map<String, ?> prepareAddedDishesAndSumCost() {
        var availableAppetizers = appetizerService.findAvailable(
                ChooseRestaurantToFoodOrderController.uniqueCodeRestaurantToOrderFood);
        var availableSoups = soupService.findAvailable(
                ChooseRestaurantToFoodOrderController.uniqueCodeRestaurantToOrderFood);
        var availableMainMeals = mainMealService.findAvailable(
                ChooseRestaurantToFoodOrderController.uniqueCodeRestaurantToOrderFood);
        var availableDeserts = desertService.findAvailable(
                ChooseRestaurantToFoodOrderController.uniqueCodeRestaurantToOrderFood);
        var availableDrinks = drinkService.findAvailable(
                ChooseRestaurantToFoodOrderController.uniqueCodeRestaurantToOrderFood);
        return Map.of(
                "appetizerDTOsAdded", appetizerSet,
                "soupDTOsAdded", soupSet,
                "mainMealDTOsAdded", mainMealSet,
                "desertDTOsAdded", desertSet,
                "drinkDTOsAdded", drinkSet,
                "appetizerDTOs", availableAppetizers,
                "soupDTOs", availableSoups,
                "mainMealDTOs", availableMainMeals,
                "desertDTOs", availableDeserts,
                "drinkDTOs", availableDrinks
        );
    }


}
