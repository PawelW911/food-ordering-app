package org.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.app.domain.*;

import java.util.Set;

@Data
@AllArgsConstructor
public class RestaurantDTO {

    String uniqueCode;
    String name;
    String typeFood;
    String email;
    String phone;
    String openingHours;
    Menu menu;
    Set<StreetDelivery> streetDelivery;
    Address address;
    Set<Opinion> opinions;
    Set<FoodOrder> orders;
    String ownerEmail;
}
