package org.app.util;

import lombok.EqualsAndHashCode;
import lombok.experimental.UtilityClass;
import org.app.domain.Menu;

@UtilityClass
@EqualsAndHashCode
public class MenuFixtures {

    public static Menu someMenuPolishFood() {
        return Menu.builder()
                .name("Polish food")
                .description("Traditional polish dishes.")
                .restaurant(RestaurantFixtures.someRestaurant1())
                .appetizers(AppetizerFixtures.someAppetizersForPolishFood())
                .soups(SoupFixtures.someSoupsForPolishFood())
                .mainMeals(MainMealFixtures.someMainMealForPolishFood())
                .deserts(DesertFixtures.someDesertsForPolishFood())
                .drinks(DrinkFixtures.someDrinksForPolishFood())
                .build();
    }
}
