package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.OwnerDTO;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.VariableDTO;
import org.app.api.dto.mapper.OwnerDTOMapper;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.RestaurantService;
import org.app.domain.Owner;
import org.app.infrastructure.zipCode.ZipCodeImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class OwnerController {

    public static final String OWNER = "/owner";
    public static final String CHOOSE_RESTAURANT = "/choose_restaurant";

    private final RestaurantService restaurantService;
    private final RestaurantDTOMapper restaurantDTOMapper;
    private final OwnerDTOMapper ownerDTOMapper;

    protected static String uniqueCodeNow;

    @GetMapping(value = OWNER)
    public String ownerPage(Model model) {
        Owner ownerExample = Owner.builder()
                .name("Tomek")
                .surname("Kowalski")
                .email("tomek1@gmail.com")
                .phone("+48 565 642 543")
                .build();
        OwnerDTO ownerDTO = ownerDTOMapper.mapToDTO(ownerExample);

        List<RestaurantDTO> availableRestaurant = restaurantService
                .findAvailableRestaurantByOwner(ownerDTOMapper.mapFromDTO(ownerDTO))
                .stream()
                .map(restaurantDTOMapper::mapToDTO)
                .toList();

        model.addAttribute("availableRestaurantDTOs", availableRestaurant);
        model.addAttribute("variableDTO", new VariableDTO());

        return "owner_portal";
    }

    @PostMapping(value = OWNER + CHOOSE_RESTAURANT)
    public String chooseRestaurantToManage(
            @Valid @ModelAttribute("variableDTO") VariableDTO variableDTO
    ) {
        uniqueCodeNow = variableDTO.getUniqueCode();
        return "redirect:/restaurant/manage";
    }

}
