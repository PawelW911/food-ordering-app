package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.security.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CustomerController {

    public static final String CUSTOMER = "/customer";

    private UserRepository userRepository;

    protected static String emailCustomer;

    @GetMapping(value = CUSTOMER)
    public String customerPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        emailCustomer = userRepository.findByUserName(userDetails.getUsername()).getEmail();

        return "customer_portal";
    }
}
