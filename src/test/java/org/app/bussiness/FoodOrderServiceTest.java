package org.app.bussiness;

import org.app.api.controller.dataForController.ForFoodOrderChoose;
import org.app.bussiness.dao.FoodOrderDAO;
import org.app.domain.Customer;
import org.app.domain.FoodOrder;
import org.app.util.CustomerFixtures;
import org.app.util.FoodOrderFixtures;
import org.app.util.RestaurantFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class FoodOrderServiceTest {

    @Mock
    private FoodOrderDAO foodOrderDAO;

    @Mock
    private AppetizerService appetizerService;

    @Mock
    private MainMealService mainMealService;

    @Mock
    private DesertService desertService;

    @Mock
    private DrinkService drinkService;

    @Mock
    private SoupService soupService;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private FoodOrderService foodOrderService;

    @Test
    void checkSaveNewFoodOrder() {
        // given
        FoodOrder foodOrderExample = FoodOrderFixtures.someFoodOrder2();

        Mockito.when(foodOrderDAO.saveFoodOrder(Mockito.any(FoodOrder.class))).thenReturn(foodOrderExample);
        Mockito.when(appetizerService
                .saveNewAppetizers(Mockito.anyList())).thenReturn(foodOrderExample.getAppetizers().stream().toList());
        Mockito.when(mainMealService
                .saveNewMainMeals(Mockito.anyList())).thenReturn(foodOrderExample.getMainMeals().stream().toList());
        Mockito.when(desertService
                .saveNewDeserts(Mockito.anyList())).thenReturn(foodOrderExample.getDeserts().stream().toList());
        Mockito.when(drinkService
                .saveNewDrinks(Mockito.anyList())).thenReturn(foodOrderExample.getDrinks().stream().toList());
        Mockito.when(soupService
                .saveNewSoups(Mockito.anyList())).thenReturn(foodOrderExample.getSoups().stream().toList());

        // when
        FoodOrder foodOrder = foodOrderService.saveNewFoodOrder(foodOrderExample);
        // then
        Assertions.assertNotNull(foodOrder);
        Assertions.assertEquals(new BigDecimal("188.00"), foodOrder.getSumCost());
        Assertions.assertTrue(foodOrder.getAppetizers().containsAll(foodOrderExample.getAppetizers()));
        Assertions.assertTrue(foodOrder.getMainMeals().containsAll(foodOrderExample.getMainMeals()));
        Assertions.assertTrue(foodOrder.getDeserts().containsAll(foodOrderExample.getDeserts()));
        Assertions.assertTrue(foodOrder.getDrinks().containsAll(foodOrderExample.getDrinks()));
        Assertions.assertTrue(foodOrder.getSoups().containsAll(foodOrderExample.getSoups()));

    }

    @Test
    void checkFindFoodOrdersByCustomer() {
        // given
        Customer customerExample = CustomerFixtures.someCustomer1();
        FoodOrder foodOrderExample = FoodOrderFixtures.someFoodOrder2();

        Mockito.when(foodOrderDAO.findByCustomer(customerExample)).thenReturn(Set.of(foodOrderExample));
        // when
        Set<FoodOrder> foodOrders = foodOrderService.findFoodOrdersByCustomer(customerExample);

        // when
        Assertions.assertTrue(foodOrders.size() > 0);
        foodOrders.forEach(Assertions::assertNotNull);
    }

    @Test
    void checkFindFoodOrderByFoodOrderNumber() {
        // given
        FoodOrder foodOrderExample = FoodOrderFixtures.someFoodOrder2();
        String foodOrderNumber = FoodOrderFixtures.someFoodOrder2().getFoodOrderNumber();

        Mockito.when(foodOrderDAO.findByFoodOrderNumber(
                        foodOrderNumber,
                        ForFoodOrderChoose.MAP_WITHOUT_SET_DISHES.toString()
                ))
                .thenReturn(foodOrderExample);
        // when
        FoodOrder foodOrder = foodOrderService.findByFoodOrderNumber(
                foodOrderNumber,
                ForFoodOrderChoose.MAP_WITHOUT_SET_DISHES.toString()
        );

        // then
        Assertions.assertNotNull(foodOrderNumber);
    }

    @Test
    void checkFindAvailableByRestaurant() {
        // given
        String uniqueCodeRestaurant = RestaurantFixtures.someRestaurant1().getUniqueCode();

        Mockito.when(foodOrderDAO.findAvailableByRestaurant(RestaurantFixtures.someRestaurant1()))
                .thenReturn(Set.of(FoodOrderFixtures.someFoodOrder1()));
        Mockito.when(restaurantService.findByUniqueCode(uniqueCodeRestaurant))
                .thenReturn(RestaurantFixtures.someRestaurant1());
        // when
        Set<FoodOrder> foodOrders = foodOrderService.findAvailableByRestaurantUniqueCode(uniqueCodeRestaurant);

        // then
        Assertions.assertEquals(1, foodOrders.size());
    }

}
