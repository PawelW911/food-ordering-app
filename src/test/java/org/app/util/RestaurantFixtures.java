package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.Restaurant;
import org.app.infrastructure.database.entity.RestaurantEntity;

@UtilityClass
public class RestaurantFixtures {

    public static Restaurant someRestaurant1() {
        return Restaurant.builder()
                .uniqueCode("WAW45")
                .name("Polish food")
                .typeFood("Polish traditional food")
                .email("polish-food@gmail.com")
                .phone("+48 643 764 532")
                .menu(MenuFixtures.someMenuPolishFood())
                .streetDelivery(StreetDeliveryFixtures.someStreetDelivery1())
                .address(AddressFixtures.someAddress1())
                .build();
    }
}
