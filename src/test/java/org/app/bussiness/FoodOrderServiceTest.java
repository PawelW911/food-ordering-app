package org.app.bussiness;

import org.app.bussiness.dao.FoodOrderDAO;
import org.app.domain.FoodOrder;
import org.app.util.FoodOrderFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

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
}
