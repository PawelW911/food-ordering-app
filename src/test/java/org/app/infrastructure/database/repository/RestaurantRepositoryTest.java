package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Restaurant;
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
// popracuj nad konfiguracja
    private final RestaurantRepository restaurantRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final OwnerRepository ownerRepository;

    private void saveCustomer() {
        ownerRepository.saveOwner(OwnerFixtures.someOwner1());
    }

    @Test
    void correctlySaveRestaurant() {
        saveCustomer();
        // given
        Restaurant restaurant = RestaurantFixtures.someRestaurant1();

        // when
        List<RestaurantEntity> allRestaurantBeforeSave = restaurantJpaRepository.findAll();
        restaurantRepository.saveRestaurant(restaurant);
        List<RestaurantEntity> allRestaurantAfterSave = restaurantJpaRepository.findAll();
        // then
        Assertions.assertThat(allRestaurantBeforeSave).hasSize(allRestaurantAfterSave.size()-1);
    }
}
