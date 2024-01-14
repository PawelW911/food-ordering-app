package org.app.bussiness;

import org.app.business.dao.RestaurantDAO;
import org.app.domain.Restaurant;
import org.app.util.RestaurantFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    private RestaurantDAO restaurantDAO;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void checkCorrectlySaveNewRestaurant() {
        // given
        Restaurant restaurantExample = RestaurantFixtures.someRestaurant1();

        Mockito.when(restaurantDAO.saveRestaurant(Mockito.any(Restaurant.class))).thenReturn(restaurantExample);
        // when
        Restaurant restaurant = restaurantService.saveNewRestaurant(restaurantExample);

        //then
        Assertions.assertNotNull(restaurant);

    }
}
