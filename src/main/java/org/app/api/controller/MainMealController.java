package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.MainMealDTO;
import org.app.api.dto.mapper.MainMealMapperDTO;
import org.app.bussiness.MainMealService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.domain.MainMeal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static org.app.api.controller.OwnerController.OWNER;
import static org.app.api.controller.OwnerController.uniqueCodeNow;

@Controller
@AllArgsConstructor
public class MainMealController {

    public static final String ADD_MAIN_MEAL = "/add_main_meal";
    public static final String DELETE_MAIN_MEAL = "/delete_main_meal";

    private MainMealService mainMealService;
    private MainMealMapperDTO mainMealMapperDTO;
    private MenuService menuService;
    private RestaurantService restaurantService;

    @PostMapping(value = OWNER + ADD_MAIN_MEAL)
    public String addMainMeal(
            @Valid @ModelAttribute("mainMealDTO") MainMealDTO mainMealDTO
    ) {
        MainMeal mainMeal = mainMealService.saveNewMainMeal(mainMealMapperDTO
                .mapFromDTO(
                        mainMealDTO,
                        0,
                        menuService.findByRestaurant(restaurantService
                                .findByUniqueCode(uniqueCodeNow))
                ));
        return "redirect:/owner/restaurant/manage/menu";
    }

    @DeleteMapping(value = OWNER + DELETE_MAIN_MEAL)
    public String deleteMainMeal(
            @Valid @ModelAttribute("mainMealDTO") MainMealDTO mainMealDTO
    ) {
        mainMealService.deleteMainMeal(mainMealDTO.getMainMealId());
        return "redirect:/owner/restaurant/manage/menu";
    }


}
