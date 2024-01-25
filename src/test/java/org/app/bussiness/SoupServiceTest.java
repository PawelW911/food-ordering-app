package org.app.bussiness;

import org.app.bussiness.dao.SoupDAO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.domain.Soup;
import org.app.util.AppetizerFixtures;
import org.app.util.MenuFixtures;
import org.app.util.RestaurantFixtures;
import org.app.util.SoupFixtures;
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
public class SoupServiceTest {

    @Mock
    private SoupDAO soupDAO;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private SoupService soupService;

    @Test
    void checkSaveNewSoups() {
        // given
        List<Soup> soupsExample = SoupFixtures.someSoupsForPolishFood().stream().toList();

        Mockito.when(soupDAO.saveSoups(Mockito.anyList())).thenReturn(soupsExample);

        // when
        List<Soup> soups = soupService.saveNewSoups(soupsExample);

        // then
        Assertions.assertEquals(soupsExample.size(), soups.size());
    }

    @Test
    void checkSaveNewSoup() {
        // given
        Soup soupExample = SoupFixtures.someSoupsForPolishFood().stream().toList().get(0);

        Mockito.when(soupDAO.saveSoup(Mockito.any(Soup.class))).thenReturn(soupExample);

        // when

        Soup soup = soupService.saveNewSoup(soupExample);

        // then
        Assertions.assertNotNull(soup);
    }

    @Test
    void checkFindAvailable() {
        // given
        Set<Soup> soupsExample = SoupFixtures.someSoupsForPolishFood();
        Mockito.when(restaurantService.findByUniqueCode(Mockito.anyString()))
                .thenReturn(RestaurantFixtures.someRestaurant1());
        Mockito.when(menuService.findByRestaurant(Mockito.any(Restaurant.class)))
                .thenReturn(MenuFixtures.someMenuPolishFood());
        Mockito.when(soupDAO.findAvailableByMenu(Mockito.any(Menu.class))).thenReturn(soupsExample);

        // when
        Set<Soup> soups = soupService.findAvailable(RestaurantFixtures.someRestaurant1().getUniqueCode());

        // then
        Assertions.assertEquals(soupsExample.size(), soups.size());

    }
}
