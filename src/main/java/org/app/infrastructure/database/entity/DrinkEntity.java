package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "drinkId")
@ToString(of = {"drinkId", "name", "composition", "price", "alcoholFree"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drink")
public class DrinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drink_id")
    private Integer drinkId;

    @Column(name = "name")
    private String name;

    @Column(name = "composition")
    private String composition;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "alcohol_free")
    private Boolean alcoholFree;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    MenuEntity menu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_order_id")
    FoodOrderEntity foodOrder;

}
