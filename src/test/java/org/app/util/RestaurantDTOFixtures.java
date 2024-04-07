package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.api.dto.RestaurantDTO;

@UtilityClass
public class RestaurantDTOFixtures {

    public static RestaurantDTO someRestaurantDTO() {
        return RestaurantDTO.builder()
                .uniqueCode("WAW45")
                .restaurantName("Polish food")
                .restaurantTypeFood("Polish traditional food")
                .restaurantEmail("polish-food@gmail.com")
                .restaurantPhone("+48 643 764 532")
                .restaurantOpeningHours("12:00-22:00")
                .restaurantAddressStreet("Katowicka")
                .restaurantAddressLocalNumber("56")
                .restaurantAddressPostalCode("00-452")
                .restaurantAddressCity("Warszawa")
                .build();
    }
}
