package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.app.api.dto.MenuDTO;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.mapper.MenuMapperDTO;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.MenuService;
import org.app.bussiness.OwnerService;
import org.app.bussiness.RestaurantService;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

import static org.app.api.controller.OwnerController.*;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class AddNewRestaurantController {

    public static final String RESTAURANT = "/restaurant";
    public static final String ADD_NEW_RESTAURANT = "/add_new_restaurant";
    public static final String ADD_NEW_RESTAURANT_SUBMIT = "/add_new_restaurant_submit";
    public static final String ADD_MENU = "/add_menu";

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantDTOMapper restaurantDTOMapper;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapperDTO menuMapperDTO;

    private static String uniqueCode;

    @GetMapping(value = OWNER + RESTAURANT + ADD_NEW_RESTAURANT)
    public String restaurantAddNewPage(ModelMap model) {
        model.addAttribute("restaurantDTO", new RestaurantDTO());

        return "add_new_restaurant";
    }

    @PostMapping(value = OWNER + RESTAURANT + ADD_NEW_RESTAURANT_SUBMIT)
    public String addRestaurant(
            @Valid @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO,
            ModelMap model
    ) {
        Restaurant restaurant = restaurantService.saveNewRestaurant(restaurantDTOMapper
                .mapFromDTO(
                        restaurantDTO,
                        ownerService.findByEmail(ownerEmail),
                        UUID.randomUUID().toString()
                ));
        RestaurantDTO restaurantDTOFind =
                restaurantDTOMapper.mapToDTO(restaurantService.findByUniqueCode(restaurant.getUniqueCode()));

        model.addAttribute("restaurantUniqueCode", restaurantDTOFind.getUniqueCode());
        model.addAttribute("restaurantName", restaurantDTOFind.getRestaurantName());
        model.addAttribute("restaurantTypeFood", restaurantDTOFind.getRestaurantTypeFood());
        model.addAttribute("restaurantEmail", restaurantDTOFind.getRestaurantEmail());
        model.addAttribute("restaurantPhone", restaurantDTOFind.getRestaurantPhone());
        model.addAttribute("restaurantOpeningHours", restaurantDTOFind.getRestaurantOpeningHours());

        model.addAttribute("restaurantAddressStreet", restaurantDTOFind.getRestaurantAddressStreet());
        model.addAttribute("restaurantAddressLocalNumber", restaurantDTOFind.getRestaurantAddressLocalNumber());
        model.addAttribute("restaurantAddressPostalCode", restaurantDTOFind.getRestaurantAddressPostalCode());
        model.addAttribute("restaurantAddressCity", restaurantDTOFind.getRestaurantAddressCity());

        uniqueCode = restaurantDTOFind.getUniqueCode();

        model.addAttribute("menuDTO", new MenuDTO());

        return "add_restaurant_done";
    }

    @PostMapping(value = OWNER + RESTAURANT + ADD_MENU)
    public String addMenu(
            @Valid @ModelAttribute("menuDTO") MenuDTO menuDTO,
            ModelMap model
    ) {
        Menu menu = menuService
                .saveNewMenu(menuMapperDTO.mapFromDTO(menuDTO, restaurantService.findByUniqueCode(uniqueCode)));
        MenuDTO menuDTOFind = menuMapperDTO.mapToDto(menu);

        model.addAttribute("menuName", menuDTOFind.getMenuName());
        model.addAttribute("menuDescription", menuDTOFind.getMenuDescription());

        return "menu_add_successful";
    }
}
