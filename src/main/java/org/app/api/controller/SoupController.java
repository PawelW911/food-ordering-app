package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.AppetizerDTO;
import org.app.api.dto.SoupDTO;
import org.app.api.dto.mapper.AppetizerMapperDTO;
import org.app.api.dto.mapper.SoupMapperDTO;
import org.app.bussiness.AppetizerService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.bussiness.SoupService;
import org.app.domain.Appetizer;
import org.app.domain.Soup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class SoupController {

    public static final String ADD_SOUP = "/add_soup";
    public static final String DELETE_SOUP = "/delete_soup";

    SoupService soupService;
    SoupMapperDTO soupMapperDTO;
    MenuService menuService;
    RestaurantService restaurantService;

    @PostMapping(value = ADD_SOUP)
    public String addSoup(
            @Valid @ModelAttribute("soupDTO") SoupDTO soupDTO
    ) {
        Soup soup = soupService.saveNewSoup(soupMapperDTO
        .mapFromDTO(
                soupDTO,
                0,
                menuService.findByRestaurant(restaurantService
                        .findByUniqueCode(OwnerController.uniqueCodeNow))
        ));
        return "redirect:/restaurant/manage/menu";
    }

    @DeleteMapping(value = DELETE_SOUP)
    public String deleteSoup(
            @Valid @ModelAttribute("soupDTO") SoupDTO soupDTO
    ) {
        Integer soupId = soupDTO.getSoupId();
        soupService.deleteSoup(soupDTO.getSoupId());
        return "redirect:/restaurant/manage/menu";
    }



}
