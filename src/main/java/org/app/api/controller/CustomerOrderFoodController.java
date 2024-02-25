package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.app.api.dto.*;
import org.app.bussiness.*;
import org.app.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.app.api.controller.dataForController.ForFoodOrderChoose.*;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class CustomerOrderFoodController {

    public static final String CUSTOMER_ORDERS = "/customer_orders";
    public static final String UPDATE_ORDER = "/update_order";
    public static final String UPDATE_APPETIZER_ORDER = "/update_appetizer_order";
    public static final String UPDATE_QUANTITY_APPETIZER_ORDER = "/update_quantity_appetizer_order";
    public static final String UPDATE_SOUP_ORDER = "/update_soup_order";
    public static final String UPDATE_QUANTITY_SOUP_ORDER = "/update_quantity_soup_order";
    public static final String UPDATE_MAIN_MEAL_ORDER = "/update_main_meal_order";
    public static final String UPDATE_QUANTITY_MAIN_MEAL_ORDER = "/update_quantity_main_meal_order";
    public static final String UPDATE_DESERT_ORDER = "/update_desert_order";
    public static final String UPDATE_QUANTITY_DESERT_ORDER = "/update_quantity_desert_order";
    public static final String UPDATE_DRINK_ORDER = "/update_drink_order";
    public static final String UPDATE_QUANTITY_DRINK_ORDER = "/update_quantity_drink_order";


    private FoodOrderService foodOrderService;
    private CustomerService customerService;
    private AppetizerService appetizerService;
    private SoupService soupService;
    private MainMealService mainMealService;
    private DesertService desertService;
    private DrinkService drinkService;

    protected static String foodOrderNumber;

    @GetMapping(value = CUSTOMER_ORDERS)
    public ModelAndView customerOrdersPage(Model model) {
        model.addAttribute("variableDTO", new VariableDTO());


        Map<String, Set<FoodOrder>> customerOrders = prepareCustomerOrders();
        model.addAttribute("completedOrderToOpinion", customerOrders.get("foodOrderDTOs").stream()
                .filter(order -> !(order.getCompletedDateTime() == null))
                .collect(Collectors.toSet()));
        return new ModelAndView("customer_order_food", customerOrders);
    }

    @GetMapping(value = UPDATE_ORDER)
    public ModelAndView updateOrder(
            Model model,
            @Valid @ModelAttribute("variableDTO") VariableDTO variableDTO
    ) {
        if (!(variableDTO.getFoodOrderNumber() == null)) {
            foodOrderNumber = variableDTO.getFoodOrderNumber();
        }
        foodOrderService.updateSumCost(foodOrderNumber);
        model.addAttribute("appetizerDTO", new AppetizerDTO());
        model.addAttribute("soupDTO", new SoupDTO());
        model.addAttribute("mainMealDTO", new MainMealDTO());
        model.addAttribute("desertDTO", new DesertDTO());
        model.addAttribute("drinkDTO", new DrinkDTO());
        model.addAttribute("sumCost", foodOrderService.findByFoodOrderNumber(
                foodOrderNumber, MAP_WITHOUT_SET_DISHES.toString()).getSumCost());

        Map<String, ?> orderDishes = prepareOrderDishesAndAvailableDishes();
        return new ModelAndView("update_order", orderDishes);
    }

    @PutMapping(UPDATE_APPETIZER_ORDER)
    public String addAppetizerToOrder(
            Model model,
            @Valid @ModelAttribute("appetizerDTO") AppetizerDTO appetizerDTO
    ) {
        Appetizer appetizerFound = appetizerService.findById(appetizerDTO.getAppetizerId());

        if (appetizerDTO.getQuantity() > 0) {
            appetizerService.saveNewAppetizer(appetizerFound
                    .withAppetizerId(null)
                    .withQuantity(appetizerDTO.getQuantity())
                    .withFoodOrder(foodOrderService.findByFoodOrderNumber(
                            foodOrderNumber,
                            MAP_WITHOUT_SET_DISHES.toString()
                    ))
            );
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }


    @PutMapping(UPDATE_QUANTITY_APPETIZER_ORDER)
    public String updateQuantityAppetizerInOrder(
            Model model,
            @Valid @ModelAttribute("appetizerDTO") AppetizerDTO appetizerDTO
    ) {
        Appetizer appetizerFound = appetizerService.findById(appetizerDTO.getAppetizerId());

        if (appetizerDTO.getQuantity() == 0) {
            appetizerService.deleteAppetizer(appetizerDTO.getAppetizerId());
        } else {
            appetizerService.updateQuantityAppetizer(appetizerDTO.getAppetizerId(), appetizerDTO.getQuantity());
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }

    @PutMapping(UPDATE_SOUP_ORDER)
    public String addSoupToOrder(
            Model model,
            @Valid @ModelAttribute("soupDTO") SoupDTO soupDTO
    ) {
        Soup soupFound = soupService.findById(soupDTO.getSoupId());

        if (soupDTO.getQuantity() > 0) {
            soupService.saveNewSoup(soupFound
                    .withSoupId(null)
                    .withQuantity(soupDTO.getQuantity())
                    .withFoodOrder(foodOrderService.findByFoodOrderNumber(
                            foodOrderNumber,
                            MAP_WITHOUT_SET_DISHES.toString()
                    ))
            );
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }


    @PutMapping(UPDATE_QUANTITY_SOUP_ORDER)
    public String updateQuantitySoupInOrder(
            Model model,
            @Valid @ModelAttribute("soupDTO") SoupDTO soupDTO
    ) {
        Soup soupFound = soupService.findById(soupDTO.getSoupId());

        if (soupDTO.getQuantity() == 0) {
            soupService.deleteSoup(soupDTO.getSoupId());
        } else {
            soupService.updateQuantitySoup(soupDTO.getSoupId(), soupDTO.getQuantity());
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }

    @PutMapping(UPDATE_MAIN_MEAL_ORDER)
    public String addMainMealToOrder(
            Model model,
            @Valid @ModelAttribute("mainMealDTO") MainMealDTO mainMealDTO
    ) {
        MainMeal mainMealFound = mainMealService.findById(mainMealDTO.getMainMealId());

        if (mainMealDTO.getQuantity() > 0) {
            mainMealService.saveNewMainMeal(mainMealFound
                    .withMainMealId(null)
                    .withQuantity(mainMealDTO.getQuantity())
                    .withFoodOrder(foodOrderService.findByFoodOrderNumber(
                            foodOrderNumber,
                            MAP_WITHOUT_SET_DISHES.toString()
                    ))
            );
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }


    @PutMapping(UPDATE_QUANTITY_MAIN_MEAL_ORDER)
    public String updateQuantityMainMealInOrder(
            Model model,
            @Valid @ModelAttribute("mainMealDTO") MainMealDTO mainMealDTO
    ) {
        MainMeal mainMealFound = mainMealService.findById(mainMealDTO.getMainMealId());

        if (mainMealDTO.getQuantity() == 0) {
            mainMealService.deleteMainMeal(mainMealDTO.getMainMealId());
        } else {
            mainMealService.updateQuantityMainMeal(mainMealDTO.getMainMealId(), mainMealDTO.getQuantity());
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }

    @PutMapping(UPDATE_DESERT_ORDER)
    public String addDesertToOrder(
            Model model,
            @Valid @ModelAttribute("desertDTO") DesertDTO desertDTO
    ) {
        Desert desertFound = desertService.findById(desertDTO.getDesertId());

        if (desertDTO.getQuantity() > 0) {
            desertService.saveNewDesert(desertFound
                    .withDesertId(null)
                    .withQuantity(desertDTO.getQuantity())
                    .withFoodOrder(foodOrderService.findByFoodOrderNumber(
                            foodOrderNumber,
                            MAP_WITHOUT_SET_DISHES.toString()
                    ))
            );
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }


    @PutMapping(UPDATE_QUANTITY_DESERT_ORDER)
    public String updateQuantityDesertInOrder(
            Model model,
            @Valid @ModelAttribute("desertDTO") DesertDTO desertDTO
    ) {
        Desert desertFound = desertService.findById(desertDTO.getDesertId());

        if (desertDTO.getQuantity() == 0) {
            desertService.deleteDesert(desertDTO.getDesertId());
        } else {
            desertService.updateQuantityDesert(desertDTO.getDesertId(), desertDTO.getQuantity());
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }

    @PutMapping(UPDATE_DRINK_ORDER)
    public String addDrinkToOrder(
            Model model,
            @Valid @ModelAttribute("drinkDTO") DrinkDTO drinkDTO
    ) {
        Drink drinkFound = drinkService.findById(drinkDTO.getDrinkId());

        if (drinkDTO.getQuantity() > 0) {
            drinkService.saveNewDrink(drinkFound
                    .withDrinkId(null)
                    .withQuantity(drinkDTO.getQuantity())
                    .withFoodOrder(foodOrderService.findByFoodOrderNumber(
                            foodOrderNumber,
                            MAP_WITHOUT_SET_DISHES.toString()
                    ))
            );
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }


    @PutMapping(UPDATE_QUANTITY_DRINK_ORDER)
    public String updateQuantityDrinkInOrder(
            Model model,
            @Valid @ModelAttribute("drinkDTO") DrinkDTO drinkDTO
    ) {
        Drink drinkFound = drinkService.findById(drinkDTO.getDrinkId());

        if (drinkDTO.getQuantity() == 0) {
            drinkService.deleteDrink(drinkDTO.getDrinkId());
        } else {
            drinkService.updateQuantityDrink(drinkDTO.getDrinkId(), drinkDTO.getQuantity());
        }
        model.addAttribute("variableDTO", new VariableDTO());
        return "redirect:/update_order";
    }



    private Map<String, ?> prepareOrderDishesAndAvailableDishes() {
        FoodOrder foodOrder = foodOrderService.findByFoodOrderNumber(
                foodOrderNumber,
                MAP_ONLY_SET_DISHES.toString()
        );
        var appetizers = foodOrder.getAppetizers();
        var soups = foodOrder.getSoups();
        var mainMeals = foodOrder.getMainMeals();
        var deserts = foodOrder.getDeserts();
        var drinks = foodOrder.getDrinks();
        String uniqueCode = foodOrder.getRestaurant().getUniqueCode();
        var availableAppetizers = appetizerService.findAvailable(uniqueCode);
        var availableSoups = soupService.findAvailable(uniqueCode);
        var availableMainMeals = mainMealService.findAvailable(uniqueCode);
        var availableDeserts = desertService.findAvailable(uniqueCode);
        var availableDrinks  = drinkService.findAvailable(uniqueCode);
        return Map.of(
                "orderAppetizersDTOs", appetizers,
                "orderSoupsDTOs", soups,
                "orderMainMealsDTOs", mainMeals,
                "orderDesertsDTOs", deserts,
                "orderDrinksDTOs", drinks,
                "appetizerDTOs", availableAppetizers,
                "soupDTOs", availableSoups,
                "mainMealDTOs", availableMainMeals,
                "desertDTOs", availableDeserts,
                "drinkDTOs", availableDrinks
        );
    }

    private Map<String, Set<FoodOrder>> prepareCustomerOrders() {
        var availableOrders = foodOrderService
                .findFoodOrdersByCustomer(customerService.findCustomerByEmail(CustomerController.emailCustomer));
        return Map.of("foodOrderDTOs", availableOrders);
    }
}
