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

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    Set<MainMealEntity> mainMeals;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    Set<AppetizerEntity> appetizers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    Set<SoupEntity> soups;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    Set<DesertEntity> deserts;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    Set<DrinkEntity> drinks;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    RestaurantEntity restaurant;

}
