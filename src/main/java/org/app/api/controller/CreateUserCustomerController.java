package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.UserDTO;
import org.app.bussiness.CustomerService;
import org.app.domain.Address;
import org.app.domain.Customer;
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
public class CreateUserCustomerController {

    public static final String CREATE_USER_CUSTOMER = "/create_user_customer";
    public static final String SUBMIT_USER_CUSTOMER = "/submit_user_customer";

    private CustomerService customerService;

    @GetMapping(value = CREATE_USER_CUSTOMER)
    public String createUserPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "create_user_customer_page";
    }

    @PostMapping(SUBMIT_USER_CUSTOMER)
    public String submitUser(
            @Valid @ModelAttribute("userDTO") UserDTO userDTO
    ) {
        Customer customer = Customer.builder()
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
                                .id(2)
                                .role("CUSTOMER")
                                .build()))
                        .build())
                .address(Address.builder()
                        .street(userDTO.getStreet())
                        .localNumber(userDTO.getLocalNumber())
                        .postalCode(userDTO.getPostalCode())
                        .city(userDTO.getCity())
                        .build())
                .build();
        customerService.saveNewCustomer(customer);
        return "create_user_success";
    }
}