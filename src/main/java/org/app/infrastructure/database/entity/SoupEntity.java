package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "soupId")
@ToString(of = {"soupId", "name", "composition", "price"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "soup")
public class SoupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soup_id")
    private Integer soupId;

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
