package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.MainMeal;
import org.app.infrastructure.database.entity.MainMealEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class MainMealFixtures {
    public static Set<MainMeal> someMainMealForPolishFood() {
        Set<MainMeal> mainMeals = new HashSet<>();
        mainMeals.add(MainMeal.builder()
                        .name("Pork chop with potatoes")
                        .composition("Pork chop, potatoes, salad")
                        .price(new BigDecimal("32.00"))
                .quantity(0)
                .build());
        mainMeals.add(MainMeal.builder()
                        .name("Dumplings with meat")
                        .composition("meat")
                        .price(new BigDecimal("23.00"))
                .quantity(0)
                .build());
        return mainMeals;
    }
}
