package org.app.api.dto;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.domain.FoodOrder;
import org.app.domain.Menu;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesertDTO {

    private Integer desertId;
    private String name;
    private String composition;
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Incorrect price format")
    private BigDecimal price;
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Incorrect quantity format")
    private Integer quantity;
    private Menu menu;
    private FoodOrder foodOrder;
}
