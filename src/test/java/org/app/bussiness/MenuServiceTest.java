package org.app.bussiness;

import org.app.business.dao.MenuDAO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.util.MenuFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuDAO menuDAO;

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
    private MenuService menuService;

    @Test
    void checkNewMenu() {
        // given
        Menu menuExample = MenuFixtures.someMenuPolishFood();

        Mockito.when(menuDAO.saveMenu(Mockito.any(Menu.class))).thenReturn(menuExample);
        Mockito.when(appetizerService
                .saveNewAppetizers(Mockito.anyList())).thenReturn(menuExample.getAppetizers().stream().toList());
        Mockito.when(mainMealService
                .saveNewMainMeals(Mockito.anyList())).thenReturn(menuExample.getMainMeals().stream().toList());
        Mockito.when(desertService
                .saveNewDeserts(Mockito.anyList())).thenReturn(menuExample.getDeserts().stream().toList());
        Mockito.when(drinkService
                .saveNewDrinks(Mockito.anyList())).thenReturn(menuExample.getDrinks().stream().toList());
        Mockito.when(soupService
                .saveNewSoups(Mockito.anyList())).thenReturn(menuExample.getSoups().stream().toList());

        //
        Menu menu = menuService.saveNewMenu(menuExample);

        // when
        Assertions.assertNotNull(menu);
        Assertions.assertTrue(menu.getAppetizers().containsAll(menuExample.getAppetizers()));
        Assertions.assertTrue(menu.getMainMeals().containsAll(menuExample.getMainMeals()));
        Assertions.assertTrue(menu.getDeserts().containsAll(menuExample.getDeserts()));
        Assertions.assertTrue(menu.getDrinks().containsAll(menuExample.getDrinks()));
        Assertions.assertTrue(menu.getSoups().containsAll(menuExample.getSoups()));
    }


}
