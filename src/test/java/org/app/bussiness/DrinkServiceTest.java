package org.app.bussiness;

import org.app.bussiness.dao.DrinkDAO;
import org.app.domain.Drink;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.util.DrinkFixtures;
import org.app.util.MenuFixtures;
import org.app.util.RestaurantFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class DrinkServiceTest {

    @Mock
    private DrinkDAO drinkDAO;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private DrinkService drinkService;

    @Test
    void checkSaveNewDrinks() {
        // given
        List<Drink> drinksExample = DrinkFixtures.someDrinksForPolishFood().stream().toList();

        Mockito.when(drinkDAO.saveDrinks(Mockito.anyList())).thenReturn(drinksExample);

        // given
        List<Drink> drinks = drinkService.saveNewDrinks(drinksExample);

        // then
        Assertions.assertEquals(drinksExample.size(), drinks.size());
    }

    @Test
    void checkSaveNewDrink() {
        // given
        Drink drinkExample = DrinkFixtures.someDrinksForPolishFood().stream().toList().get(0);

        Mockito.when(drinkDAO.saveDrink(Mockito.any(Drink.class))).thenReturn(drinkExample);

        // when

        Drink drink = drinkService.saveNewDrink(drinkExample);

        // then
        Assertions.assertNotNull(drink);
    }

    @Test
    void checkFindAvailable() {
        // given
        Set<Drink> drinksExample = DrinkFixtures.someDrinksForPolishFood();
        Mockito.when(restaurantService.findByUniqueCode(Mockito.anyString()))
                .thenReturn(RestaurantFixtures.someRestaurant1());
        Mockito.when(menuService.findByRestaurant(Mockito.any(Restaurant.class)))
                .thenReturn(MenuFixtures.someMenuPolishFood());
        Mockito.when(drinkDAO.findAvailableByMenu(Mockito.any(Menu.class))).thenReturn(drinksExample);

        // when
        Set<Drink> drinks = drinkService.findAvailable(RestaurantFixtures.someRestaurant1().getUniqueCode());

        // then
        Assertions.assertEquals(drinksExample.size(), drinks.size());

    }

    @Test
    void checkCorrectlyFindById() {
        // given
        Drink drinkExample = DrinkFixtures.someDrinksForPolishFood().stream().toList().get(0)
                .withDrinkId(1);

        Mockito.when(drinkDAO.findById(drinkExample.getDrinkId())).thenReturn(drinkExample);
        // when
        Drink drink = drinkService.findById(drinkExample.getDrinkId());

        // then
        Assertions.assertNotNull(drink);

    }

    @Test
    void checkUpdateQuantityDrink() {
        // given
        Drink drinkExample = DrinkFixtures.someDrinksForPolishFood().stream().toList().get(0)
                .withDrinkId(1);
        Integer quantity = 5;

        Mockito.when(drinkDAO.updateQuantityDrink(drinkExample.getDrinkId(), quantity))
                .thenReturn(drinkExample.withQuantity(quantity));
        // when
        Drink drink = drinkService.updateQuantityDrink(drinkExample.getDrinkId(), quantity);

        // then
        Assertions.assertNotNull(drink);
    }
}
