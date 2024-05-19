package org.app.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.app.api.dto.PostalCodeDTO;
import org.app.bussiness.StreetDeliveryService;
import org.app.bussiness.dao.ZipCodeDAO;
import org.app.domain.StreetDelivery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.stream.Collectors;

import static org.app.api.controller.OwnerController.OWNER;
import static org.app.api.controller.OwnerController.uniqueCodeNow;

@Controller
@AllArgsConstructor
public class ManageStreetDeliveryController {

    public static final String MANAGE_STREET_DELIVERY = "/manage_street_delivery";
    public static final String ADD_STREET_DELIVERY = "/add_street_delivery";

    private StreetDeliveryService streetDeliveryService;
    private ZipCodeDAO zipCodeDAO;

    @GetMapping(value = OWNER + MANAGE_STREET_DELIVERY)
    public ModelAndView manageStreetDeliveryPage(Model model) {
        model.addAttribute("postalCodeDTO", new PostalCodeDTO());
        Map<String, ?> modelPostalCode = preparePostalCode();
        return new ModelAndView("street_delivery_manage", modelPostalCode);
    }

    @PostMapping(value = OWNER + ADD_STREET_DELIVERY)
    public String addStreetDeliveryByPostalCode(
            @Valid @ModelAttribute("postalCodeDTO") PostalCodeDTO postalCodeDTO
    ) {
        zipCodeDAO.getStreetDeliveryByZipCode(postalCodeDTO.getPostalCode());
        streetDeliveryService.saveNewStreetsDelivery(postalCodeDTO.getPostalCode(), uniqueCodeNow);

        return "redirect:/owner/manage_street_delivery";
    }

    protected Map<String, ?> preparePostalCode() {
        var postalCodes = streetDeliveryService
                .postalCodeByRestaurantUniqueCode(uniqueCodeNow)
                .stream()
                .collect(Collectors.groupingBy(StreetDelivery::getPostalCode))
                .keySet();
        return Map.of("deliveryPostalCodeDTOs", postalCodes);
    }
}
