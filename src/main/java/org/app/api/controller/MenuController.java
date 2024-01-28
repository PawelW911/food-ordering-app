package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.DrinkDTO;
import org.app.api.dto.MenuDTO;
import org.app.api.dto.mapper.MenuMapperDTO;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@AllArgsConstructor
public class MenuController {

    public static final String UPDATE_MENU = "/update_menu";

    MenuService menuService;
    MenuMapperDTO menuMapperDTO;
    RestaurantService restaurantService;

    @PutMapping(value = UPDATE_MENU)
    public String updateMenu(
            @Valid @ModelAttribute("menuDTO") MenuDTO menuDTO
    ) {
        menuService.updateMenu(menuMapperDTO
                .mapFromDTO(menuDTO, restaurantService.findByUniqueCode(OwnerController.uniqueCodeNow)));

        return "redirect:/restaurant/manage/menu";
    }
}
