package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CustomerController {

    public static final String CUSTOMER = "/customer";

    @GetMapping(value = CUSTOMER)
    public String customerPage(Model model) {
        return "customer_portal";
    }
}
