package org.app.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"appetizerId", "name", "composition", "price", "quantity", "foodOrder"})
public class Appetizer {

    Integer appetizerId;
    String name;
    String composition;
    BigDecimal price;
    Integer quantity;
    Menu menu;
    FoodOrder foodOrder;
}
