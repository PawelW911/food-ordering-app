package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.*;
import org.app.bussiness.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@AllArgsConstructor
public class ManageRestaurantController {

    public static final String RESTAURANT = "/restaurant";
    public static final String MANAGE = "/manage";
    public static final String MENU = "/menu";

    AppetizerService appetizerService;
    SoupService soupService;
    MainMealService mainMealService;
    DesertService desertService;
    DrinkService drinkService;

    @GetMapping(value = RESTAURANT + MANAGE)
    public String manageRestaurantPage() {
        return "manage_restaurant";
    }

    @GetMapping(value = RESTAURANT + MANAGE + MENU)
    public ModelAndView manageRestaurantManageMenuPage(Model model) {
        model.addAttribute("appetizerDTO", new AppetizerDTO());
        model.addAttribute("soupDTO", new SoupDTO());
        model.addAttribute("mainMealDTO", new MainMealDTO());
        model.addAttribute("desertDTO", new DesertDTO());
        model.addAttribute("drinkDTO", new DrinkDTO());
        Map<String, ?> modelMenu = prepareMenuPositions();
        return new ModelAndView("manage_restaurant_menu", modelMenu);
    }

    private Map<String, ?> prepareMenuPositions() {
        var availableAppetizers = appetizerService.findAvailable(OwnerController.uniqueCodeNow);
        var availableSoups = soupService.findAvailable(OwnerController.uniqueCodeNow);
        var availableMainMeals = mainMealService.findAvailable(OwnerController.uniqueCodeNow);
        var availableDeserts = desertService.findAvailable(OwnerController.uniqueCodeNow);
        var availableDrinks = drinkService.findAvailable(OwnerController.uniqueCodeNow);
        return Map.of(
                "appetizerDTOs", availableAppetizers,
                "soupDTOs", availableSoups,
                "mainMealDTOs", availableMainMeals,
                "desertDTOs", availableDeserts,
                "drinkDTOs", availableDrinks
        );
    }

//    @PostMapping(value = RESTAURANT + MANAGE)
//    public String manageRestaurant(@RequestParam("selectedRestaurant") String selectedRestaurant, Model model) {
//        model.addAttribute("uniqueCode", selectedRestaurant);
//        return "redirect:/restaurant/manage";
//    }

}
