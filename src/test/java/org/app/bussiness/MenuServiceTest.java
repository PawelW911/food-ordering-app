package org.app.bussiness;

import org.app.bussiness.dao.MenuDAO;
import org.app.domain.Menu;
import org.app.util.MenuFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuDAO menuDAO;


    @InjectMocks
    private MenuService menuService;

    @Test
    void checkNewMenu() {
        // given
        Menu menuExample = MenuFixtures.someMenuPolishFood();

        Mockito.when(menuDAO.saveMenu(Mockito.any(Menu.class))).thenReturn(menuExample);
        //
        Menu menu = menuService.saveNewMenu(menuExample);

        // when
        Assertions.assertNotNull(menu);
    }

    @Test
    void checkCorrectlyUpdateMenu() {
        // given
        Menu menuExample = MenuFixtures.someMenuPolishFoodUpdate();

        Mockito.when(menuDAO.updateMenu(Mockito.any(Menu.class))).thenReturn(menuExample);
        // when
        Menu menuAfterUpdate = menuService.updateMenu(menuExample);

        // then
        Assertions.assertInstanceOf(Menu.class, menuAfterUpdate);
    }


}
