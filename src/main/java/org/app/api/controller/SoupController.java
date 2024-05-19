package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.SoupDTO;
import org.app.api.dto.mapper.SoupMapperDTO;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.bussiness.SoupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static org.app.api.controller.OwnerController.OWNER;
import static org.app.api.controller.OwnerController.uniqueCodeNow;

@Controller
@AllArgsConstructor
public class SoupController {

    public static final String ADD_SOUP = "/add_soup";
    public static final String DELETE_SOUP = "/delete_soup";

    private SoupService soupService;
    private SoupMapperDTO soupMapperDTO;
    private MenuService menuService;
    private RestaurantService restaurantService;

    @PostMapping(value = OWNER + ADD_SOUP)
    public String addSoup(
            @Valid @ModelAttribute("soupDTO") SoupDTO soupDTO
    ) {
        soupService.saveNewSoup(soupMapperDTO
                .mapFromDTO(
                        soupDTO,
                        0,
                        menuService.findByRestaurant(restaurantService
                                .findByUniqueCode(uniqueCodeNow))
                ));
        return "redirect:/owner/restaurant/manage/menu";
    }

    @DeleteMapping(value = OWNER + DELETE_SOUP)
    public String deleteSoup(
            @Valid @ModelAttribute("soupDTO") SoupDTO soupDTO
    ) {
        soupService.deleteSoup(soupDTO.getSoupId());
        return "redirect:/owner/restaurant/manage/menu";
    }


}
