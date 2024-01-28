package org.app.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"menuId", "name", "description","appetizers", "soups", "mainMeals", "deserts", "drinks", "restaurant"})
public class Menu {

    Integer menuId;
    String name;
    String description;
    Set<Appetizer> appetizers;
    Set<Soup> soups;
    Set<MainMeal> mainMeals;
    Set<Desert> deserts;
    Set<Drink> drinks;
    Restaurant restaurant;

}
