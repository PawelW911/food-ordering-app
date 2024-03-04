package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.UserDTO;
import org.app.bussiness.OwnerService;
import org.app.domain.Owner;
import org.app.domain.Role;
import org.app.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
@AllArgsConstructor
public class CreateUserOwnerController {

    public static final String CREATE_USER_OWNER = "/create_user_owner";
    public static final String SUBMIT_USER_OWNER = "/submit_user_owner";

    private OwnerService ownerService;

    @GetMapping(value = CREATE_USER_OWNER)
    public String createUserPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "create_user_owner_page";
    }

    @PostMapping(SUBMIT_USER_OWNER)
    public String submitUser(
            @Valid @ModelAttribute("userDTO") UserDTO userDTO
    ) {
        Owner owner = Owner.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .user(User.builder()
                        .userName(userDTO.getUserName())
                        .email(userDTO.getEmail())
                        .password(userDTO.getPassword())
                        .active(true)
                        .roles(Set.of(Role.builder()
                                .id(1)
                                .role("OWNER")
                                .build()))
                        .build())
                .build();
        ownerService.saveNewOwner(owner);
        return "create_user_success";
    }
}
