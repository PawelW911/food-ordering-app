package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.MenuDTO;
import org.app.api.dto.RestaurantDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MenuController {

    public static final String MENU = "/menu";
    public static final String ADD_MENU = "/add_menu";

    @GetMapping(value = MENU + ADD_MENU)
    public String ownerPage(ModelMap model) {
        model.addAttribute("menuDTO", new MenuDTO());
        return "add_new_menu_restaurant";
    }


}
