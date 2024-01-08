package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Restaurant;
import org.app.infrastructure.configuration.PersistenceContainerTestConfiguration;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.repository.RestaurantRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantRepositoryTest {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;

    @Test
    void correctlyAddRestaurant() {
        // given
        Restaurant restaurant = RestaurantFixtures.someRestaurant1();

        // when
        restaurantRepository.saveRestaurant(restaurant);
        RestaurantEntity restaurantEntity = restaurantJpaRepository.findByUniqueCode(restaurant.getUniqueCode());
        System.out.println(restaurantEntity.toString());
        // then
        Assertions.assertThatObject(restaurantEntity).isNotNull();
    }
}
