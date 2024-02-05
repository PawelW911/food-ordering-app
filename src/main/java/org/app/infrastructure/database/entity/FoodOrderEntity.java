package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "foodOrderId")
@ToString(of = {"foodOrderId", "appetizers", "soups", "mainMeals", "deserts", "drinks"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_order")
public class FoodOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_order_id")
    private Integer foodOrderId;

    @Column(name = "food_order_number")
    private String foodOrderNumber;

    @Column(name = "received_date_time")
    private OffsetDateTime receivedDateTime;

    @Column(name = "completed_date_time")
    private OffsetDateTime completedDateTime;

    @Column(name = "sum_cost")
    private BigDecimal sumCost;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    RestaurantEntity restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    CustomerEntity customer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "foodOrder", cascade = CascadeType.ALL)
    Set<AppetizerEntity> appetizers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "foodOrder", cascade = CascadeType.ALL)
    Set<SoupEntity> soups;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "foodOrder", cascade = CascadeType.ALL)
    Set<MainMealEntity> mainMeals;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "foodOrder", cascade = CascadeType.ALL)
    Set<DesertEntity> deserts;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "foodOrder", cascade = CascadeType.ALL)
    Set<DrinkEntity> drinks;

}
