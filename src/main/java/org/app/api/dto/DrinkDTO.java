package org.app.api.dto;

import lombok.*;
import org.app.domain.FoodOrder;
import org.app.domain.Menu;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDTO {

    Integer drinkId;
    String name;
    String composition;
    BigDecimal price;
    Integer quantity;
    Boolean alcoholFree;
    Menu menu;
    FoodOrder foodOrder;
}
