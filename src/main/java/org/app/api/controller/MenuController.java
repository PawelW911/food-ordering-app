package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.MenuDTO;
import org.app.api.dto.mapper.MenuMapperDTO;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;

import static org.app.api.controller.OwnerController.OWNER;
import static org.app.api.controller.OwnerController.uniqueCodeNow;

@Controller
@AllArgsConstructor
public class MenuController {

    public static final String UPDATE_MENU = "/update_menu";

    private MenuService menuService;
    private MenuMapperDTO menuMapperDTO;
    private RestaurantService restaurantService;

    @PutMapping(value = OWNER + UPDATE_MENU)
    public String updateMenu(
            @Valid @ModelAttribute("menuDTO") MenuDTO menuDTO
    ) {
        menuService.updateMenu(menuMapperDTO
                .mapFromDTO(menuDTO, restaurantService.findByUniqueCode(uniqueCodeNow)));

        return "redirect:/owner/restaurant/manage/menu";
    }
}
