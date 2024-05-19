package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.VariableDTO;
import org.app.api.dto.mapper.RestaurantDTOMapper;
import org.app.bussiness.OwnerService;
import org.app.bussiness.RestaurantService;
import org.app.security.UserEntity;
import org.app.security.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final OwnerService ownerService;
    private final UserRepository userRepository;

    protected static String uniqueCodeNow;
    protected static String ownerEmail;

    @GetMapping(value = OWNER)
    public String ownerPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        UserEntity userLogNow = userRepository.findByUserName(username);
        ownerEmail = userLogNow.getEmail();
        List<RestaurantDTO> availableRestaurant = restaurantService
                .findAvailableRestaurantByOwner(ownerService.findByEmail(ownerEmail))
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
        return "redirect:/owner/restaurant/manage";
    }

}
