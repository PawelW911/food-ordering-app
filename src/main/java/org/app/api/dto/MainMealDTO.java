package org.app.api.dto;

import lombok.*;
import org.app.domain.FoodOrder;
import org.app.domain.Menu;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainMealDTO {

    Integer mainMealId;
    String name;
    String composition;
    BigDecimal price;
    Integer quantity;
    Menu menu;
    FoodOrder foodOrder;
}
