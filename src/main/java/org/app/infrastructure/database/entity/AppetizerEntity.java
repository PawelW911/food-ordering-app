package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "appetizerId")
@ToString(of = {"appetizerId", "name", "composition", "price", "quantity", "foodOrder"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appetizer")
public class AppetizerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appetizer_id")
    private Integer appetizerId;

    @Column(name = "name")
    private String name;

    @Column(name = "composition")
    private String composition;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    MenuEntity menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_order_id")
    FoodOrderEntity foodOrder;




}
