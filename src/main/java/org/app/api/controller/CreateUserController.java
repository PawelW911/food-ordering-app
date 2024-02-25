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
public class CreateUserController {

    public static final String CREATE_USER = "/create_user";
    public static final String SUBMIT_USER = "/submit_user";

    private OwnerService ownerService;

    @GetMapping(value = CREATE_USER)
    public String createUserPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "create_user_page";
    }

    @PostMapping(SUBMIT_USER)
    public String submitUser(
            @Valid @ModelAttribute("userDTO") UserDTO userDTO
    ) {
        if (userDTO.isUserType()) {
            Owner owner = Owner.builder()
                    .name(userDTO.getName())
                    .surname(userDTO.getSurname())
                    .email(userDTO.getEmail())
                    .phone(userDTO.getPhone())
                    .user(User.builder()
                            .userName(userDTO.getUserName())
                            .email(userDTO.getEmail())
                            .password(userDTO.getPassword())
                            .roles(Set.of(Role.builder()
                                    .role("OWNER")
                                    .build()))
                            .build())
                    .build();
            ownerService.saveNewOwner(owner);
            return "create_user_success";
        }
        return "";
    }
}
