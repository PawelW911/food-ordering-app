package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.DesertDTO;
import org.app.api.dto.MainMealDTO;
import org.app.api.dto.mapper.DesertMapperDTO;
import org.app.api.dto.mapper.MainMealMapperDTO;
import org.app.bussiness.DesertService;
import org.app.bussiness.MainMealService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.domain.Desert;
import org.app.domain.MainMeal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static org.app.api.controller.OwnerController.*;

@Controller
@AllArgsConstructor
public class DesertController {

    public static final String ADD_DESERT = "/add_desert";
    public static final String DELETE_DESERT = "/delete_desert";

    DesertService desertService;
    DesertMapperDTO desertMapperDTO;
    MenuService menuService;
    RestaurantService restaurantService;

    @PostMapping(value = OWNER + ADD_DESERT)
    public String addDesert(
            @Valid @ModelAttribute("desertDTO") DesertDTO desertDTO
    ) {
        Desert desert = desertService.saveNewDesert(desertMapperDTO
        .mapFromDTO(
                desertDTO,
                0,
                menuService.findByRestaurant(restaurantService
                        .findByUniqueCode(uniqueCodeNow))
        ));
        return "redirect:/owner/restaurant/manage/menu";
    }

    @DeleteMapping(value = OWNER + DELETE_DESERT)
    public String deleteDesert(
            @Valid @ModelAttribute("desertDTO") DesertDTO desertDTO
    ) {
        Integer desertId = desertDTO.getDesertId();
        desertService.deleteDesert(desertDTO.getDesertId());
        return "redirect:/owner/restaurant/manage/menu";
    }



}
