package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.DesertDTO;
import org.app.api.dto.DrinkDTO;
import org.app.api.dto.mapper.DesertMapperDTO;
import org.app.api.dto.mapper.DrinkMapperDTO;
import org.app.bussiness.DesertService;
import org.app.bussiness.DrinkService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.domain.Desert;
import org.app.domain.Drink;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static org.app.api.controller.OwnerController.*;

@Controller
@AllArgsConstructor
public class DrinkController {

    public static final String ADD_DRINK = "/add_drink";
    public static final String DELETE_DRINK = "/delete_drink";

    private DrinkService drinkService;
    private DrinkMapperDTO drinkMapperDTO;
    private MenuService menuService;
    private RestaurantService restaurantService;

    @PostMapping(value = OWNER + ADD_DRINK)
    public String addDrink(
            @Valid @ModelAttribute("drinkDTO") DrinkDTO drinkDTO
    ) {
        Drink drink = drinkService.saveNewDrink(drinkMapperDTO
        .mapFromDTO(
                drinkDTO,
                0,
                menuService.findByRestaurant(restaurantService
                        .findByUniqueCode(uniqueCodeNow))
        ));
        return "redirect:/owner/restaurant/manage/menu";
    }

    @DeleteMapping(value = OWNER + DELETE_DRINK)
    public String deleteDrink(
            @Valid @ModelAttribute("drinkDTO") DrinkDTO drinkDTO
    ) {
        Integer drinkId = drinkDTO.getDrinkId();
        drinkService.deleteDrink(drinkDTO.getDrinkId());
        return "redirect:/owner/restaurant/manage/menu";
    }



}
