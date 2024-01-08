package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "menuId")
@ToString(of = {"menuId", "appetizers", "soups", "mainMeals", "deserts", "drinks"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "main_meal_id")
    Set<MainMealEntity> mainMeals;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "appetizer_id")
    Set<AppetizerEntity> appetizers;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "soup_id")
    Set<SoupEntity> soups;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "desert_id")
    Set<DesertEntity> deserts;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "drink_id")
    Set<DrinkEntity> drinks;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "menu")
//    private RestaurantEntity restaurant;


}
