package org.app.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "orderNumber")
@ToString(of = {
        "orderId", "orderNumber", "receivedDateTime", "completedDateTime",
        "restaurant", "customer", "appetizers", "sumCost",
        "soups", "mainMeals", "deserts", "drinks"
})
public class Order {

    Integer orderId;
    String orderNumber;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    BigDecimal sumCost;
    Restaurant restaurant;
    Customer customer;
    Set<Appetizer> appetizers;
    Set<Soup> soups;
    Set<MainMeal> mainMeals;
    Set<Desert> deserts;
    Set<Drink> drinks;
}
