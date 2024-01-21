package org.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.domain.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    String uniqueCode;
    String restaurantName;
    String restaurantTypeFood;
    String restaurantEmail;
    String restaurantPhone;
    String restaurantOpeningHours;
    String restaurantAddressStreet;
    String restaurantAddressLocalNumber;
    String restaurantAddressPostalCode;
    String restaurantAddressCity;
    String ownerEmail;
    Menu menu;
    Set<StreetDelivery> streetDelivery;
    Address address;
    Set<Opinion> opinions;
    Set<FoodOrder> orders;
    Owner owner;
}
