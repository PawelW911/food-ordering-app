package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.OwnerDTO;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.mapper.OwnerDTOMapper;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.RestaurantService;
import org.app.domain.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class OwnerController {

    public static final String OWNER = "/owner";

    private final RestaurantService restaurantService;
    private final RestaurantDTOMapper restaurantDTOMapper;
    private final OwnerDTOMapper ownerDTOMapper;

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

        return "owner_portal";
    }

}
