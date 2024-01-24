package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.AppetizerDTO;
import org.app.bussiness.AppetizerService;
import org.app.bussiness.MenuService;
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

    @GetMapping(value = RESTAURANT + MANAGE)
    public String manageRestaurantPage() {
        return "manage_restaurant";
    }

    @GetMapping(value = RESTAURANT + MANAGE + MENU)
    public ModelAndView manageRestaurantManageMenuPage(Model model) {
        model.addAttribute("appetizerDTO", new AppetizerDTO());
        Map<String, ?> modelMenu = prepareMenuPositions();
        return new ModelAndView("manage_restaurant_menu", modelMenu);
    }

    private Map<String,?> prepareMenuPositions() {
        var availableAppetizers = appetizerService.findAvailable(OwnerController.uniqueCodeNow);
        return Map.of("appetizerDTOs", availableAppetizers);
    }

//    @PostMapping(value = RESTAURANT + MANAGE)
//    public String manageRestaurant(@RequestParam("selectedRestaurant") String selectedRestaurant, Model model) {
//        model.addAttribute("uniqueCode", selectedRestaurant);
//        return "redirect:/restaurant/manage";
//    }

}
