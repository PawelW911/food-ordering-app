package org.app.api.controller.rest;

import lombok.AllArgsConstructor;
import org.app.api.dto.AvailableStreetsDeliveryDTO;
import org.app.api.dto.mapper.StreetDeliveryMapperDTO;
import org.app.bussiness.StreetDeliveryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(StreetDeliveryRestController.API_STREET_DELIVERY)
public class StreetDeliveryRestController {

    public static final String API_STREET_DELIVERY = "/api/street_delivery";
    public static final String AVAILABLE_STREET_DELIVERY = "/available_street_delivery";

    private final StreetDeliveryService streetDeliveryService;
    private final StreetDeliveryMapperDTO streetDeliveryMapperDTO;
    @GetMapping(value = AVAILABLE_STREET_DELIVERY)
    public AvailableStreetsDeliveryDTO availableStreetsDeliveryDTO() {
        return getAvailableStreetsDeliveryDTO();
    }

    private AvailableStreetsDeliveryDTO getAvailableStreetsDeliveryDTO() {
        return AvailableStreetsDeliveryDTO.builder()
                .streetsDelivery(streetDeliveryService.findAvailableStreetsDelivery().stream()
                        .map(streetDeliveryMapperDTO::mapToDTO)
                        .toList())
                .build();
    }
}
