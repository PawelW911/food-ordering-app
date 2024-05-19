package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Owner;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;



@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final OwnerRepository ownerRepository;

    private void saveOwner() {
        ownerRepository.saveOwner(OwnerFixtures.someOwner1());
    }
    private void saveRestaurantFirst() {
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
    }
    private void saveRestaurantSecond() {
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant2());
    }

    @Test
    void correctlySaveRestaurant() {
        saveOwner();
        // given
        Restaurant restaurant = RestaurantFixtures.someRestaurant1();

        // when
        List<RestaurantEntity> allRestaurantBeforeSave = restaurantJpaRepository.findAll();
        restaurantRepository.saveRestaurant(restaurant);
        List<RestaurantEntity> allRestaurantAfterSave = restaurantJpaRepository.findAll();
        // then
        Assertions.assertThat(allRestaurantBeforeSave).hasSize(allRestaurantAfterSave.size()-1);
    }

    @Test
    void correctlyFindRestaurantByCityAndStreetDeliveryAvailable() {
        saveOwner();
        saveRestaurantFirst();
        // given
        Restaurant restaurantExample = RestaurantFixtures.someRestaurant1();
        StreetDelivery streetDeliveryExample = restaurantExample.getStreetDelivery().stream().toList().get(0);

        // when
        List<Restaurant> restaurants = restaurantRepository.findRestaurantByStreetDelivery(streetDeliveryExample);

        // then
        Assertions.assertThat(restaurants).hasSize(1);
    }

    @Test
    void correctlyFindRestaurantByOwner() {
        saveOwner();
        saveRestaurantFirst();
        // given
        Owner ownerExample = OwnerFixtures.someOwner1();

        // when
        List<Restaurant> restaurants = restaurantRepository.findAvailableRestaurantByOwner(ownerExample);

        // then
        Assertions.assertThat(restaurants).hasSize(1);
    }

    @Test
    void correctlyFindRestaurantByUniqueCode() {
        saveOwner();
        saveRestaurantFirst();
        // given
        String uniqueCode = RestaurantFixtures.someRestaurant1().getUniqueCode();

        // when
        Restaurant restaurant = restaurantRepository.findByUniqueCode(uniqueCode);

        // then
        Assertions.assertThatObject(restaurant).isNotNull();
        Assertions.assertThat(restaurant.getUniqueCode()).isEqualTo(uniqueCode);
    }

    @Test
    void correctlyFindAvailableRestaurant() {
        saveOwner();
        saveRestaurantFirst();
        saveRestaurantSecond();
        // given
        int quantityRestaurants = 2;

        // when
        List<Restaurant> availableRestaurant = restaurantRepository.findAvailableRestaurant();

        // given
        Assertions.assertThat(availableRestaurant).hasSize(quantityRestaurants);

    }
}
