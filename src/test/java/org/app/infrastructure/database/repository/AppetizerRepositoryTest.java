package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.AppetizerJpaRepository;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.util.AppetizerFixtures;
import org.app.util.MenuFixtures;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AppetizerRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final AppetizerRepository appetizerRepository;
    private final AppetizerJpaRepository appetizerJpaRepository;
    private final OwnerMapper ownerMapper;
    private final OwnerJpaRepository ownerJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private void saveAppetizers() {
        appetizerRepository.saveAppetizer(AppetizerFixtures.someAppetizersForPolishFood().stream()
                .map(appetizerr -> appetizerr.withQuantity(0))
                .toList().get(0));
        appetizerRepository.saveAppetizer(AppetizerFixtures.someAppetizersForPolishFood().stream()
                .map(appetizerr -> appetizerr.withQuantity(0))
                .toList().get(1));

    }

    private Menu saveRestaurantAndOwnerAndMenu() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
        Menu menu = menuRepository.saveMenu(MenuFixtures.someMenuPolishFood());
        appetizerRepository.saveAppetizer(AppetizerFixtures.someAppetizersForPolishFood().stream()
                .map(appetizerr -> appetizerr.withQuantity(0).withMenu(menu))
                .toList().get(0));
        appetizerRepository.saveAppetizer(AppetizerFixtures.someAppetizersForPolishFood().stream()
                .map(appetizerr -> appetizerr.withQuantity(0).withMenu(menu))
                .toList().get(1));

        return menu;
    }

    @Test
    void correctlyAppetizersSave() {
        // given
        List<Appetizer> appetizers = AppetizerFixtures.someAppetizersForPolishFood().stream()
                .map(appetizer -> appetizer.withQuantity(0))
                .toList();

        // when
        List<AppetizerEntity> allAppetizersBeforeSave = appetizerJpaRepository.findAll();
        appetizerRepository.saveAppetizers(appetizers);
        List<AppetizerEntity> allAppetizerAfterSave = appetizerJpaRepository.findAll();

        // then
        Assertions.assertThat(allAppetizersBeforeSave).hasSize(allAppetizerAfterSave.size() - appetizers.size());
    }

    @Test
    void correctlyAppetizerSave() {
        // given
        Appetizer appetizer = AppetizerFixtures.someAppetizersForPolishFood().stream()
                .map(appetizerr -> appetizerr.withQuantity(0))
                .toList().get(0);

        // when
        List<AppetizerEntity> allAppetizersBeforeSave = appetizerJpaRepository.findAll();
        appetizerRepository.saveAppetizer(appetizer);
        List<AppetizerEntity> allAppetizerAfterSave = appetizerJpaRepository.findAll();

        // then
        Assertions.assertThat(allAppetizersBeforeSave).hasSize(allAppetizerAfterSave.size() - 1);
    }

    @Test
    void correctlyFindAvailable() {
        Menu menu = saveRestaurantAndOwnerAndMenu();
        // given, when
        List<AppetizerEntity> allBefore = appetizerJpaRepository.findAll();
        Set<Appetizer> appetizers = appetizerRepository.findAvailableByMenu(menu);

        // then
        Assertions.assertThat(appetizers).hasSize(allBefore.size());
    }

    @Test
    void correctlyAppetizerDelete() {
        saveAppetizers();
        // given
        Integer appetizerId = appetizerJpaRepository.findAll().get(0).getAppetizerId();

        // when
        List<AppetizerEntity> allAppetizersBeforeDelete = appetizerJpaRepository.findAll();
        appetizerRepository.deleteAppetizer(appetizerId);
        List<AppetizerEntity> allAppetizersAfterDelete = appetizerJpaRepository.findAll();

        //
        Assertions.assertThat(allAppetizersBeforeDelete).hasSize(allAppetizersAfterDelete.size() + 1);

    }
}
