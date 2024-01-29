package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.AppetizerDTO;
import org.app.api.dto.PostalCodeDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ManageStreetDeliveryController {

    public static final String MANAGE_STREET_DELIVERY = "/manage_street_delivery";
    public static final String ADD_STREET_DELIVERY = "/add_street_delivery";

    @GetMapping(value = MANAGE_STREET_DELIVERY)
    public String manageStreetDeliveryPage(Model model) {
        model.addAttribute("postalCodeDTO", new PostalCodeDTO());
        return "street_delivery_manage";
    }

    @PostMapping(value = ADD_STREET_DELIVERY)
    public String addStreetDeliveryByPostalCode(
            @Valid @ModelAttribute("postalCodeDTO") PostalCodeDTO postalCodeDTO
    ) {
        return "";
    }
}
