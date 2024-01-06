package org.app.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Value
@Builder
@EqualsAndHashCode(of = "appetizerId")
@ToString(of = {"appetizerId", "name", "composition", "price", "quantity"})
public class Appetizer {

    Integer appetizerId;
    String name;
    String composition;
    BigDecimal price;
    Integer quantity;
}
