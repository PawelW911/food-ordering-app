package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.controller.dataForController.MenuPosition;
import org.app.api.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.app.api.controller.OwnerController.OWNER;
import static org.app.api.controller.OwnerController.uniqueCodeNow;

@Controller
@AllArgsConstructor
public class ManageRestaurantController {

    public static final String RESTAURANT = "/restaurant";
    public static final String MANAGE = "/manage";
    public static final String MENU = "/menu";

    private MenuPosition menuPosition;

    @GetMapping(value = OWNER + RESTAURANT + MANAGE)
    public String manageRestaurantPage() {
        return "manage_restaurant";
    }

    @GetMapping(value = OWNER + RESTAURANT + MANAGE + MENU)
    public ModelAndView manageRestaurantManageMenuPage(Model model) {
        model.addAttribute("appetizerDTO", new AppetizerDTO());
        model.addAttribute("soupDTO", new SoupDTO());
        model.addAttribute("mainMealDTO", new MainMealDTO());
        model.addAttribute("desertDTO", new DesertDTO());
        model.addAttribute("drinkDTO", new DrinkDTO());
        model.addAttribute("menuDTO", new MenuDTO());
        Map<String, ?> modelMenu = menuPosition.prepareMenuPositions(uniqueCodeNow);
        return new ModelAndView("manage_restaurant_menu", modelMenu);
    }


}
