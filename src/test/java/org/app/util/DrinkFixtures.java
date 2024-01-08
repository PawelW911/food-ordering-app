package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.Drink;
import org.app.infrastructure.database.entity.DrinkEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class DrinkFixtures {
    public static Set<Drink> someDrinksForPolishFood() {
        Set<Drink> drinks = new HashSet<>();
        drinks.add(Drink.builder()
                .name("Coffee")
                        .composition("arabic coffee, water")
                        .price(new BigDecimal("5.00"))
                        .alcoholFree(true)
                .quantity(0)
                .build());
        drinks.add(Drink.builder()
                .name("Orange juice")
                        .composition("orange, water")
                        .price(new BigDecimal("3.00"))
                .alcoholFree(true)
                .quantity(0)
                .build());
        drinks.add(Drink.builder()
                .name("Beer")
                        .composition("hop, water")
                        .price(new BigDecimal("5.50"))
                .alcoholFree(false)
                .quantity(0)
                .build());
        return drinks;
    }
}
