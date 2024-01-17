package org.app.bussiness;

import org.app.bussiness.dao.RestaurantDAO;
import org.app.domain.Owner;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    @Test
    void checkCorrectlyFindAvailableRestaurantByStreetDelivery() {
        // given
        List<Restaurant> restaurantsExample = List.of(RestaurantFixtures.someRestaurant1());
        StreetDelivery streetDelivery =
                RestaurantFixtures.someRestaurant1().getStreetDelivery().stream().toList().get(0);

        Mockito.when(restaurantDAO
                .findRestaurantByStreetDelivery(Mockito.any(StreetDelivery.class))).thenReturn(restaurantsExample);
        // when
        List<Restaurant> restaurants = restaurantService.findAvailableRestaurantByStreetDelivery(streetDelivery);

        // then
        Assertions.assertEquals(1, restaurants.size());
    }

    @Test
    void checkCorrectlyFindRestaurantByOwner() {
        // given
        List<Restaurant> restaurantsExample = List.of(RestaurantFixtures.someRestaurant1());
        Owner ownerExample = OwnerFixtures.someOwner1();

        Mockito.when(restaurantDAO
                .findAvailableRestaurantByOwner(ownerExample)).thenReturn(restaurantsExample);
        // when
        List<Restaurant> restaurants = restaurantService.findAvailableRestaurantByOwner(ownerExample);

        // then
        Assertions.assertEquals(1, restaurants.size());
    }
}
