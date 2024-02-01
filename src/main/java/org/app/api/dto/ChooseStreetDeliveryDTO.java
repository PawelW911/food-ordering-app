package org.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChooseStreetDeliveryDTO {

    String chooseStreet;
    String chooseCity;
}
