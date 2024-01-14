package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.Address;
import org.app.infrastructure.database.entity.AddressEntity;

@UtilityClass
public class AddressFixtures {
    public static Address someAddress1() {
        return Address.builder()
                .street("Deszczowa")
                .localNumber("20a")
                .postalCode("00-432")
                .city("Warszawa")
                .build();
    }

    public static Address someAddress2() {
        return Address.builder()
                .street("Katowicka")
                .localNumber("56")
                .postalCode("00-452")
                .city("Warszawa")
                .build();
    }
}
