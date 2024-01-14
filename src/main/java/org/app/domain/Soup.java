package org.app.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"soupId", "name", "composition", "price", "quantity"})
public class Soup {

    Integer soupId;
    String name;
    String composition;
    BigDecimal price;
    Integer quantity;
    Menu menu;
    FoodOrder foodOrder;
}
