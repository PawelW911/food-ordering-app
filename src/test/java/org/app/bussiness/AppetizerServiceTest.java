package org.app.bussiness;

import org.app.bussiness.dao.AppetizerDAO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.util.AppetizerFixtures;
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
public class AppetizerServiceTest {

    @Mock
    private AppetizerDAO appetizerDAO;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private AppetizerService appetizerService;

    @Test
    void checkSaveNewAppetizer() {
        // given
        Appetizer appetizerExample = AppetizerFixtures.someAppetizersForPolishFood().stream().toList().get(0);

        Mockito.when(appetizerDAO.saveAppetizer(Mockito.any(Appetizer.class))).thenReturn(appetizerExample);

        // when

        Appetizer appetizer = appetizerService.saveNewAppetizer(appetizerExample);

        // then
        Assertions.assertNotNull(appetizer);
    }

    @Test
    void checkSaveNewAppetizers() {
        // given
        List<Appetizer> appetizersExample = AppetizerFixtures.someAppetizersForPolishFood().stream().toList();

        Mockito.when(appetizerDAO.saveAppetizers(Mockito.anyList())).thenReturn(appetizersExample);

        // when
        List<Appetizer> appetizers = appetizerService.saveNewAppetizers(appetizersExample);

        // then
        Assertions.assertEquals(appetizersExample.size(), appetizers.size());
    }

    @Test
    void checkFindAvailable() {
        // given
        Set<Appetizer> appetizersExample = AppetizerFixtures.someAppetizersForPolishFood();
        Mockito.when(restaurantService.findByUniqueCode(Mockito.anyString()))
                .thenReturn(RestaurantFixtures.someRestaurant1());
        Mockito.when(menuService.findByRestaurant(Mockito.any(Restaurant.class)))
                .thenReturn(MenuFixtures.someMenuPolishFood());
        Mockito.when(appetizerDAO.findAvailableByMenu(Mockito.any(Menu.class))).thenReturn(appetizersExample);

        // when
        Set<Appetizer> appetizers = appetizerService.findAvailable(RestaurantFixtures.someRestaurant1().getUniqueCode());

        // then
        Assertions.assertEquals(appetizersExample.size(), appetizers.size());

    }


}
