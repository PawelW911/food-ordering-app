package org.app.bussiness;

import org.app.bussiness.dao.MainMealDAO;
import org.app.domain.MainMeal;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.util.MainMealFixtures;
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
public class MainMealServiceTest {

    @Mock
    private MainMealDAO mainMealDAO;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MainMealService mainMealService;

    @Test
    void checkSaveNewMainMeals() {
        // given
        List<MainMeal> mainMealsExample = MainMealFixtures.someMainMealsForPolishFood().stream().toList();

        Mockito.when(mainMealDAO.saveMainMeals(Mockito.anyList())).thenReturn(mainMealsExample);

        // when
        List<MainMeal> mainMeals = mainMealService.saveNewMainMeals(mainMealsExample);

        // then
        Assertions.assertEquals(mainMealsExample.size(), mainMeals.size());
    }

    @Test
    void checkSaveNewMainMeal() {
        // given
        MainMeal mainMealExample = MainMealFixtures.someMainMealsForPolishFood().stream().toList().get(0);

        Mockito.when(mainMealDAO.saveMainMeal(Mockito.any(MainMeal.class))).thenReturn(mainMealExample);

        // when

        MainMeal mainMeal = mainMealService.saveNewMainMeal(mainMealExample);

        // then
        Assertions.assertNotNull(mainMeal);
    }

    @Test
    void checkFindAvailable() {
        // given
        Set<MainMeal> mainMealsExample = MainMealFixtures.someMainMealsForPolishFood();
        Mockito.when(restaurantService.findByUniqueCode(Mockito.anyString()))
                .thenReturn(RestaurantFixtures.someRestaurant1());
        Mockito.when(menuService.findByRestaurant(Mockito.any(Restaurant.class)))
                .thenReturn(MenuFixtures.someMenuPolishFood());
        Mockito.when(mainMealDAO.findAvailableByMenu(Mockito.any(Menu.class))).thenReturn(mainMealsExample);

        // when
        Set<MainMeal> mainMeals = mainMealService.findAvailable(RestaurantFixtures.someRestaurant1().getUniqueCode());

        // then
        Assertions.assertEquals(mainMealsExample.size(), mainMeals.size());

    }

    @Test
    void checkCorrectlyFindById() {
        // given
        MainMeal mainMealExample = MainMealFixtures.someMainMealsForPolishFood().stream().toList().get(0)
                .withMainMealId(1);

        Mockito.when(mainMealDAO.findById(mainMealExample.getMainMealId())).thenReturn(mainMealExample);
        // when
        MainMeal mainMeal = mainMealService.findById(mainMealExample.getMainMealId());

        // then
        Assertions.assertNotNull(mainMeal);

    }

    @Test
    void checkUpdateQuantityMainMeal() {
        // given
        MainMeal mainMealExample = MainMealFixtures.someMainMealsForPolishFood().stream().toList().get(0)
                .withMainMealId(1);
        Integer quantity = 5;

        Mockito.when(mainMealDAO.updateQuantityMainMeal(mainMealExample.getMainMealId(), quantity))
                .thenReturn(mainMealExample.withQuantity(quantity));
        // when
        MainMeal mainMeal = mainMealService.updateQuantityMainMeal(mainMealExample.getMainMealId(), quantity);

        // then
        Assertions.assertNotNull(mainMeal);
    }
}
