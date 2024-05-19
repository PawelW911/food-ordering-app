package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.Restaurant;

@UtilityClass
public class RestaurantFixtures {

    public static Restaurant someRestaurant1() {
        return Restaurant.builder()
                .uniqueCode("WAW45")
                .name("Polish food")
                .typeFood("Polish traditional food")
                .email("polish-food@gmail.com")
                .phone("+48 643 764 532")
                .openingHours("12:00-22:00")
                .streetDelivery(StreetDeliveryFixtures.someStreetDelivery1())
                .address(AddressFixtures.someAddress2())
                .owner(OwnerFixtures.someOwner1())
                .build();
    }

    public static Restaurant someRestaurant2() {
        return Restaurant.builder()
                .uniqueCode("WAW60")
                .name("American food")
                .typeFood("American traditional food")
                .email("american-food@gmail.com")
                .phone("+48 543 654 453")
                .openingHours("12:00-22:00")
                .streetDelivery(StreetDeliveryFixtures.someStreetDelivery1())
                .address(AddressFixtures.someAddress3())
                .owner(OwnerFixtures.someOwner1())
                .build();
    }
}
