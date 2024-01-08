package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.Desert;
import org.app.infrastructure.database.entity.DesertEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class DesertFixtures {
    public static Set<Desert> someDesertsForPolishFood() {
        Set<Desert> deserts = new HashSet<>();
        deserts.add(Desert.builder()
                        .name("Apple pie")
                        .composition("apple, flour")
                        .price(new BigDecimal("11.00"))
                .quantity(0)
                .build());
        deserts.add(Desert.builder()
                        .name("Cheesecake")
                        .composition("white cheese, raisins")
                        .price(new BigDecimal("12.00"))
                .quantity(0)
                .build());
        return deserts;
    }
}
