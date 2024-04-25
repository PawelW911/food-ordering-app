package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.RestaurantRestDTO;
import org.app.domain.Address;

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

    public static RestaurantRestDTO someRestaurantRestDTO() {
        return RestaurantRestDTO.builder()
                .uniqueCode("WAW45")
                .name("Polish food")
                .typeFood("Polish traditional food")
                .email("polish-food@gmail.com")
                .phone("+48 643 764 532")
                .openingHours("12:00-22:00")
                .address(Address.builder()
                .street("Katowicka")
                .localNumber("56")
                .postalCode("00-452")
                .city("Warszawa")
                        .build())
                .build();
    }
}
