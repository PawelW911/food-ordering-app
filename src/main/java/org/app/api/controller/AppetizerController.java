package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.AppetizerDTO;
import org.app.api.dto.mapper.AppetizerMapperDTO;
import org.app.bussiness.AppetizerService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.domain.Appetizer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AppetizerController {

    public static final String ADD_APPETIZER = "/add_appetizer";
    public static final String DELETE_APPETIZER = "/delete_appetizer";

    AppetizerService appetizerService;
    AppetizerMapperDTO appetizerMapperDTO;
    MenuService menuService;
    RestaurantService restaurantService;

    @PostMapping(value = ADD_APPETIZER)
    public String addAppetizer(
            @Valid @ModelAttribute("appetizerDTO") AppetizerDTO appetizerDTO
    ) {
        Appetizer appetizer = appetizerService.saveNewAppetizer(appetizerMapperDTO
        .mapFromDTO(
                appetizerDTO,
                0,
                menuService.findByRestaurant(restaurantService
                        .findByUniqueCode(OwnerController.uniqueCodeNow))
        ));
        return "redirect:/restaurant/manage/menu";
    }

    @DeleteMapping(value = DELETE_APPETIZER)
    public String deleteAppetizer(
            @Valid @ModelAttribute("appetizerDTO") AppetizerDTO appetizerDTO
    ) {
        Integer appetizerId = appetizerDTO.getAppetizerId();
        appetizerService.deleteAppetizer(appetizerDTO.getAppetizerId());
        return "redirect:/restaurant/manage/menu";
    }



}
