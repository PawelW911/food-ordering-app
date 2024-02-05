package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.bussiness.CustomerService;
import org.app.domain.Address;
import org.app.domain.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CustomerController {

    public static final String CUSTOMER = "/customer";

    private CustomerService customerService;

    protected static String emailCustomer;

    @GetMapping(value = CUSTOMER)
    public String customerPage(Model model) {
        customerService.saveNewCustomer(Customer.builder()
                .name("Adam")
                .surname("Mazowiecki")
                .email("adam@gmail.com")
                .phone("+48 564 332 543")
                .address(Address.builder()
                        .street("Komornicka")
                        .localNumber("5")
                        .postalCode("00-532")
                        .city("Warszawa")
                        .build())
                .build());

        emailCustomer = "adam@gmail.com";

        return "customer_portal";
    }
}
