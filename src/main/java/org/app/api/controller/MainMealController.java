package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.MainMealDTO;
import org.app.api.dto.SoupDTO;
import org.app.api.dto.mapper.MainMealMapperDTO;
import org.app.api.dto.mapper.SoupMapperDTO;
import org.app.bussiness.MainMealService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.bussiness.SoupService;
import org.app.domain.MainMeal;
import org.app.domain.Soup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MainMealController {

    public static final String ADD_MAIN_MEAL = "/add_main_meal";
    public static final String DELETE_MAIN_MEAL = "/delete_main_meal";

    MainMealService mainMealService;
    MainMealMapperDTO mainMealMapperDTO;
    MenuService menuService;
    RestaurantService restaurantService;

    @PostMapping(value = ADD_MAIN_MEAL)
    public String addMainMeal(
            @Valid @ModelAttribute("mainMealDTO") MainMealDTO mainMealDTO
    ) {
        MainMeal mainMeal = mainMealService.saveNewMainMeal(mainMealMapperDTO
        .mapFromDTO(
                mainMealDTO,
                0,
                menuService.findByRestaurant(restaurantService
                        .findByUniqueCode(OwnerController.uniqueCodeNow))
        ));
        return "redirect:/restaurant/manage/menu";
    }

    @DeleteMapping(value = DELETE_MAIN_MEAL)
    public String deleteMainMeal(
            @Valid @ModelAttribute("mainMealDTO") MainMealDTO mainMealDTO
    ) {
        Integer mainMealId = mainMealDTO.getMainMealId();
        mainMealService.deleteMainMeal(mainMealDTO.getMainMealId());
        return "redirect:/restaurant/manage/menu";
    }



}
