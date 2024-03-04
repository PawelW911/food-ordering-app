package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.AppetizerJpaRepository;
import org.app.infrastructure.database.repository.jpa.FoodOrderJpaRepository;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.FoodOrderMapper;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.security.RoleEntity;
import org.app.security.RoleRepository;
import org.app.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    private final FoodOrderRepository foodOrderRepository;
    private final CustomerRepository customerRepository;
    private final FoodOrderJpaRepository foodOrderJpaRepository;
    private final FoodOrderMapper foodOrderMapper;
    private final RoleRepository roleRepository;


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

    private void saveFoodOrderAndCustomer() {
        customerRepository.saveCustomer(CustomerFixtures.someCustomer1());
        foodOrderRepository.saveFoodOrder(FoodOrderFixtures.someFoodOrder1());
    }

    @Test
    void correctlyAppetizersSave() {
        saveRestaurantAndOwnerAndMenu();
        saveFoodOrderAndCustomer();
        // given
        List<Appetizer> appetizers = AppetizerFixtures.someAppetizersForPolishFood().stream()
                .map(appetizer -> appetizer
                        .withQuantity(0)
                        .withFoodOrder(foodOrderMapper.mapFromEntity(
                                foodOrderJpaRepository
                                        .findByFoodOrderNumber(
                                                FoodOrderFixtures.someFoodOrder1().getFoodOrderNumber()))))
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

    @Test
    void correctlyFindByIdAppetizer() {
        saveAppetizers();
        // given
        Integer appetizerId = appetizerJpaRepository.findAll().get(0).getAppetizerId();

        // when
        Appetizer appetizer = appetizerRepository.findById(appetizerId);

        // then
        Assertions.assertThat(appetizer.getAppetizerId()).isEqualTo(appetizerId);
    }

    @Transactional
    @Test
    void correctlyUpdateQuantityAppetizer() {
        saveAppetizers();
        // given
        Integer appetizerId = appetizerJpaRepository.findAll().get(0).getAppetizerId();
        Integer quantity = 5;

        // when
        Appetizer appetizer = appetizerRepository.updateQuantityAppetizer(appetizerId, quantity);

        // then
        Integer quantityUpdate = appetizerJpaRepository.findById(appetizerId).orElseThrow().getQuantity();
        Assertions.assertThat(quantityUpdate)
                .isEqualTo(quantity);
    }
}
