package org.app.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"desertId", "name", "composition", "price", "quantity"})
public class Desert {

    Integer desertId;
    String name;
    String composition;
    BigDecimal price;
    Integer quantity;
    Menu menu;
    FoodOrder foodOrder;
}
