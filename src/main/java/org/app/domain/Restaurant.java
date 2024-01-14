package org.app.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {
        "restaurantId", "uniqueCode", "name", "typeFood",
        "email", "phone", "openingHours", "menu",
        "streetDelivery", "address", "opinions", "orders"
})
public class Restaurant {

    Integer restaurantId;
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
    Owner owner;
}
