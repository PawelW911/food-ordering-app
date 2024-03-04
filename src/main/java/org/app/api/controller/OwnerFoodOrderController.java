package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.app.api.controller.dataForController.ForFoodOrderChoose;
import org.app.api.dto.VariableDTO;
import org.app.bussiness.FoodOrderService;
import org.app.domain.FoodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.app.api.controller.OwnerController.*;
import static org.app.api.controller.dataForController.ForFoodOrderChoose.MAP_ONLY_SET_DISHES;
import static org.app.api.controller.dataForController.ForFoodOrderChoose.MAP_WITHOUT_SET_DISHES;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class OwnerFoodOrderController {

    public static final String FOOD_ORDER_OWNER = "/manage/food_order_owner";
    public static final String CHOOSE_ORDER_TO_MANAGE = "/choose_order_to_manage";
    public static final String CHOOSE_ORDER_TO_COMPLETE = "/choose_order_to_complete";

    private FoodOrderService foodOrderService;

    private static String orderNumber;

    @GetMapping(value = OWNER + FOOD_ORDER_OWNER)
    public ModelAndView listFoodOrderToManage(Model model) {
        model.addAttribute("variableDTO", new VariableDTO());
        return new ModelAndView("manage_food_orders_by_owner", prepareFoodOrdersByRestaurant());
    }

    @PostMapping(value = OWNER + CHOOSE_ORDER_TO_MANAGE)
    public ModelAndView chooseFoodOrderToManage(
            Model model,
            @Valid @ModelAttribute("variableDTO") VariableDTO variableDTO
    ) {
        orderNumber = variableDTO.getFoodOrderNumber();
        FoodOrder foodOrder = foodOrderService.
                findByFoodOrderNumber(variableDTO.getFoodOrderNumber(), MAP_WITHOUT_SET_DISHES.toString());
        FoodOrder foodOrderDishes = foodOrderService.
                findByFoodOrderNumber(variableDTO.getFoodOrderNumber(), MAP_ONLY_SET_DISHES.toString());

        model.addAttribute("detailsOrder", foodOrder);

        return new ModelAndView("manage_food_order", prepareDishesForFoodOrder(foodOrderDishes));
    }

    private Map<String, ?> prepareDishesForFoodOrder(FoodOrder foodOrderDishes) {
        return Map.of(
                "dishesDTOs", Stream.of(
                        foodOrderDishes.getAppetizers(),
                        foodOrderDishes.getSoups(),
                        foodOrderDishes.getMainMeals(),
                        foodOrderDishes.getDeserts(),
                        foodOrderDishes.getDrinks()
                        )
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet())
        );
    }

    @PutMapping(value = OWNER + CHOOSE_ORDER_TO_COMPLETE)
    public String chooseToCompleteOrder() {
        foodOrderService.updateCompletedDateTime(orderNumber);
        return "redirect:/owner/manage/food_order_owner" + FOOD_ORDER_OWNER;
    }


    private Map<String, ?> prepareFoodOrdersByRestaurant() {
        return Map.of(
                "availableFoodOrderDTOs",
                foodOrderService.findAvailableByRestaurantUniqueCode(uniqueCodeNow).stream()
                        .sorted(Comparator.comparing(FoodOrder::getReceivedDateTime))
                        .toList()
        );
    }
}
