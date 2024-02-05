package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "appetizerId")
@ToString(of = {"appetizerId", "name", "composition", "price", "quantity"})
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    MenuEntity menu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_order_id")
    FoodOrderEntity foodOrder;




}
