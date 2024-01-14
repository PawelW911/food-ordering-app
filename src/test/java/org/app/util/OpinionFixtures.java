package org.app.util;

import org.app.domain.Opinion;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OpinionFixtures {
    public static Opinion someOpinion1() {
        return Opinion.builder()
                .text("This is the best food.")
                .stars(4)
                .dateTime(OffsetDateTime.of(2022, 10, 10 , 10, 10, 15, 15, ZoneOffset.UTC))
                .customer(CustomerFixtures.someCustomer1())
                .restaurant(RestaurantFixtures.someRestaurant1())
                .build();
    }
}
