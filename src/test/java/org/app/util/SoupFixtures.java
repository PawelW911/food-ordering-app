package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.Soup;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class SoupFixtures {
    public static Set<Soup> someSoupsForPolishFood() {
        Set<Soup> soups = new HashSet<>();
        soups.add(Soup.builder()
                        .name("Zurek")
                        .composition("Potato, sausage")
                        .price(new BigDecimal("8.00"))
                .quantity(0)
                .build());
        soups.add(Soup.builder()
                        .name("Chicken broth")
                        .composition("Chicken, pasta")
                        .price(new BigDecimal("7.00"))
                .quantity(0)
                .build());
        return soups;
    }
}
