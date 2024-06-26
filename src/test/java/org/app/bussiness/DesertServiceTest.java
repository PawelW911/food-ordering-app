package org.app.bussiness;

import org.app.bussiness.dao.DesertDAO;
import org.app.domain.Desert;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.util.DesertFixtures;
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
public class DesertServiceTest {

    @Mock
    private DesertDAO desertDAO;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private DesertService desertService;

    @Test
    void checkSaveNewDeserts() {
        // given
        List<Desert> desertsExample = DesertFixtures.someDesertsForPolishFood().stream().toList();

        Mockito.when(desertDAO.saveDeserts(Mockito.anyList())).thenReturn(desertsExample);

        // when
        List<Desert> deserts = desertService.saveNewDeserts(desertsExample);

        //
        Assertions.assertEquals(desertsExample.size(), deserts.size());

    }

    @Test
    void checkSaveNewDesert() {
        // given
        Desert desertExample = DesertFixtures.someDesertsForPolishFood().stream().toList().get(0);

        Mockito.when(desertDAO.saveDesert(Mockito.any(Desert.class))).thenReturn(desertExample);

        // when

        Desert desert = desertService.saveNewDesert(desertExample);

        // then
        Assertions.assertNotNull(desert);
    }

    @Test
    void checkFindAvailable() {
        // given
        Set<Desert> desertsExample = DesertFixtures.someDesertsForPolishFood();
        Mockito.when(restaurantService.findByUniqueCode(Mockito.anyString()))
                .thenReturn(RestaurantFixtures.someRestaurant1());
        Mockito.when(menuService.findByRestaurant(Mockito.any(Restaurant.class)))
                .thenReturn(MenuFixtures.someMenuPolishFood());
        Mockito.when(desertDAO.findAvailableByMenu(Mockito.any(Menu.class))).thenReturn(desertsExample);

        // when
        Set<Desert> deserts = desertService.findAvailable(RestaurantFixtures.someRestaurant1().getUniqueCode());

        // then
        Assertions.assertEquals(desertsExample.size(), deserts.size());

    }

    @Test
    void checkCorrectlyFindById() {
        // given
        Desert desertExample = DesertFixtures.someDesertsForPolishFood().stream().toList().get(0)
                .withDesertId(1);

        Mockito.when(desertDAO.findById(desertExample.getDesertId())).thenReturn(desertExample);
        // when
        Desert desert = desertService.findById(desertExample.getDesertId());

        // then
        Assertions.assertNotNull(desert);

    }


    @Test
    void checkUpdateQuantityDesert() {
        // given
        Desert desertExample = DesertFixtures.someDesertsForPolishFood().stream().toList().get(0)
                .withDesertId(1);
        Integer quantity = 5;

        Mockito.when(desertDAO.updateQuantityDesert(desertExample.getDesertId(), quantity))
                .thenReturn(desertExample.withQuantity(quantity));
        // when
        Desert desert = desertService.updateQuantityDesert(desertExample.getDesertId(), quantity);

        // then
        Assertions.assertNotNull(desert);
    }
}
