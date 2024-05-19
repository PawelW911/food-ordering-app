package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.StreetDelivery;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class StreetDeliveryFixtures {


    public static Set<StreetDelivery> someStreetDelivery1() {
        Set<StreetDelivery> streetsDelivery = new HashSet<>();
        streetsDelivery.add(StreetDelivery.builder()
                .street("Poczdamska")
                .city("Warszawa")
                .postalCode("00-212")
                .build());
        streetsDelivery.add(StreetDelivery.builder()
                .street("Ulanska")
                .city("Warszawa")
                .postalCode("00-212")
                .build());
        streetsDelivery.add(StreetDelivery.builder()
                .street("Kwiatowa")
                .city("Warszawa")
                .postalCode("00-212")
                .build());
        streetsDelivery.add(StreetDelivery.builder()
                .street("Wielka")
                .city("Warszawa")
                .postalCode("00-265")
                .build());
        streetsDelivery.add(StreetDelivery.builder()
                .street("Morska")
                .city("Warszawa")
                .postalCode("00-764")
                .build());

        return streetsDelivery;
    }

    public static Set<StreetDelivery> someStreetDelivery2() {
        Set<StreetDelivery> streetsDelivery = new HashSet<>();
        streetsDelivery.add(StreetDelivery.builder()
                .street("Poczdamska")
                .city("Warszawa")
                .postalCode("00-212")
                .build());
        streetsDelivery.add(StreetDelivery.builder()
                .street("Ulanska")
                .city("Warszawa")
                .postalCode("00-212")
                .build());
        streetsDelivery.add(StreetDelivery.builder()
                .street("Kwiatowa")
                .city("Warszawa")
                .postalCode("00-212")
                .build());

        return streetsDelivery;
    }
}
