package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ManageRestaurantController {

    public static final String RESTAURANT = "/restaurant";
    public static final String MANAGE = "/manage";

    @GetMapping(value = RESTAURANT + MANAGE)
    public String manageRestaurantPage(Model model) {
        model.addAttribute("uniqueCode", OwnerController.uniqueCodeNow);
        return "manage_restaurant";
    }

//    @PostMapping(value = RESTAURANT + MANAGE)
//    public String manageRestaurant(@RequestParam("selectedRestaurant") String selectedRestaurant, Model model) {
//        model.addAttribute("uniqueCode", selectedRestaurant);
//        return "redirect:/restaurant/manage";
//    }

}
