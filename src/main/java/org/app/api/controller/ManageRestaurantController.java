package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.*;
import org.app.bussiness.*;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ManageRestaurantController {

    public static final String RESTAURANT = "/restaurant";
    public static final String MANAGE = "/manage";
    public static final String MENU = "/menu";

    private MenuPosition menuPosition;

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
        model.addAttribute("menuDTO", new MenuDTO());
        Map<String, ?> modelMenu = menuPosition.prepareMenuPositions(OwnerController.uniqueCodeNow);
        return new ModelAndView("manage_restaurant_menu", modelMenu);
    }




}
