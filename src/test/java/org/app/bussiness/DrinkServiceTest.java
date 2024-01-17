package org.app.bussiness;

import org.app.bussiness.dao.DrinkDAO;
import org.app.domain.Drink;
import org.app.util.DrinkFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DrinkServiceTest {

    @Mock
    private DrinkDAO drinkDAO;

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
}
