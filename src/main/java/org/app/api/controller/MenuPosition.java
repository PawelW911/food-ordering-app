package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.bussiness.*;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class MenuPosition {

    private AppetizerService appetizerService;
    private SoupService soupService;
    private MainMealService mainMealService;
    private DesertService desertService;
    private DrinkService drinkService;
    private MenuService menuService;
    private RestaurantService restaurantService;

    public Map<String, ?> prepareMenuPositions(String restaurantUniqueCode) {
        var availableAppetizers = appetizerService.findAvailable(restaurantUniqueCode);
        var availableSoups = soupService.findAvailable(restaurantUniqueCode);
        var availableMainMeals = mainMealService.findAvailable(restaurantUniqueCode);
        var availableDeserts = desertService.findAvailable(restaurantUniqueCode);
        var availableDrinks = drinkService.findAvailable(restaurantUniqueCode);
        return Map.of(
                "appetizerDTOs", availableAppetizers,
                "soupDTOs", availableSoups,
                "mainMealDTOs", availableMainMeals,
                "desertDTOs", availableDeserts,
                "drinkDTOs", availableDrinks,
                "menuDTOs", Set.of(
                        menuService.findByRestaurant(restaurantService.findByUniqueCode(restaurantUniqueCode))
                )
        );
    }
}
