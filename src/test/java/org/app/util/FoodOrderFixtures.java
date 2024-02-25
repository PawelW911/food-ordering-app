package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.FoodOrder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;

@UtilityClass
public class FoodOrderFixtures {

    public static FoodOrder someFoodOrder1() {
        return FoodOrder.builder()
                .foodOrderNumber("436465amz")
                .receivedDateTime(OffsetDateTime.of(2023, 5, 10, 18, 20, 32, 15, ZoneOffset.UTC))
                .sumCost(new BigDecimal("52.11"))
                .restaurant(RestaurantFixtures.someRestaurant1())
                .customer(CustomerFixtures.someCustomer1())
                .build();
    }

    public static FoodOrder someFoodOrder2() {
        return FoodOrder.builder()
                .foodOrderNumber("643265gdf")
                .receivedDateTime(OffsetDateTime.of(2023, 5, 10, 18, 20, 32, 15, ZoneOffset.UTC))
                .sumCost(BigDecimal.ZERO)
                .restaurant(RestaurantFixtures.someRestaurant1())
                .customer(CustomerFixtures.someCustomer1())
                .appetizers(new HashSet<>(List.of(
                        AppetizerFixtures.someAppetizersForPolishFood().stream().toList().get(0).withQuantity(2))))
                .mainMeals(new HashSet<>(List.of(
                        MainMealFixtures.someMainMealsForPolishFood().stream().toList().get(0).withQuantity(1),
                        MainMealFixtures.someMainMealsForPolishFood().stream().toList().get(1).withQuantity(1))))
                .deserts(new HashSet<>(List.of(
                        DesertFixtures.someDesertsForPolishFood().stream().toList().get(0).withQuantity(3))))
                .drinks(new HashSet<>(List.of(
                        DrinkFixtures.someDrinksForPolishFood().stream().toList().get(0).withQuantity(1),
                        DrinkFixtures.someDrinksForPolishFood().stream().toList().get(1).withQuantity(4))))
                .soups(new HashSet<>(List.of(
                        SoupFixtures.someSoupsForPolishFood().stream().toList().get(1).withQuantity(5))))
                .build();
    }


}
