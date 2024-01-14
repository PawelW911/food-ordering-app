package org.app.bussiness;

import org.app.business.dao.MainMealDAO;
import org.app.domain.MainMeal;
import org.app.util.MainMealFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MainMealServiceTest {

    @Mock
    private MainMealDAO mainMealDAO;

    @InjectMocks
    private MainMealService mainMealService;

    @Test
    void checkSaveNewMainMeals() {
        // given
        List<MainMeal> mainMealsExample = MainMealFixtures.someMainMealForPolishFood().stream().toList();

        Mockito.when(mainMealDAO.saveMainMeals(Mockito.anyList())).thenReturn(mainMealsExample);

        // when
        List<MainMeal> mainMeals = mainMealService.saveNewMainMeals(mainMealsExample);

        // then
        Assertions.assertEquals(mainMealsExample.size(), mainMeals.size());
    }
}
