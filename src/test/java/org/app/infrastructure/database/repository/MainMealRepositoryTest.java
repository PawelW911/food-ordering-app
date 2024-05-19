package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.MainMeal;
import org.app.domain.Menu;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.MainMealEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.MainMealJpaRepository;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.util.MainMealFixtures;
import org.app.util.MenuFixtures;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MainMealRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final MainMealRepository mainMealRepository;
    private final MainMealJpaRepository mainMealJpaRepository;
    private final OwnerMapper ownerMapper;
    private final OwnerJpaRepository ownerJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private void saveMainMeals() {
        mainMealRepository.saveMainMeal(MainMealFixtures.someMainMealsForPolishFood().stream()
                .map(mainMealr -> mainMealr.withQuantity(0))
                .toList().get(0));
        mainMealRepository.saveMainMeal(MainMealFixtures.someMainMealsForPolishFood().stream()
                .map(mainMealr -> mainMealr.withQuantity(0))
                .toList().get(1));

    }

    private Menu saveRestaurantAndOwnerAndMenu() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
        Menu menu = menuRepository.saveMenu(MenuFixtures.someMenuPolishFood());
        mainMealRepository.saveMainMeal(MainMealFixtures.someMainMealsForPolishFood().stream()
                .map(mainMealr -> mainMealr.withQuantity(0).withMenu(menu))
                .toList().get(0));
        mainMealRepository.saveMainMeal(MainMealFixtures.someMainMealsForPolishFood().stream()
                .map(mainMealr -> mainMealr.withQuantity(0).withMenu(menu))
                .toList().get(1));

        return menu;
    }

    @Test
    void correctlySaveMainMeals() {
        
        // given
        List<MainMeal> mainMeals = MainMealFixtures.someMainMealsForPolishFood().stream()
                .map(mainMeal -> mainMeal.withQuantity(0))
                .toList();

        // when
        List<MainMealEntity> allMainMealBeforeSave = mainMealJpaRepository.findAll();
        mainMealRepository.saveMainMeals(mainMeals);
        List<MainMealEntity> allMainMealAfterSave = mainMealJpaRepository.findAll();

        // then
        Assertions.assertThat(allMainMealBeforeSave).hasSize(allMainMealAfterSave.size() - mainMeals.size());
    }

    @Test
    void correctlyMainMealSave() {
        // given
        MainMeal mainMeal = MainMealFixtures.someMainMealsForPolishFood().stream()
                .map(mainMealr -> mainMealr.withQuantity(0))
                .toList().get(0);

        // when
        List<MainMealEntity> allMainMealsBeforeSave = mainMealJpaRepository.findAll();
        mainMealRepository.saveMainMeal(mainMeal);
        List<MainMealEntity> allMainMealAfterSave = mainMealJpaRepository.findAll();

        // then
        Assertions.assertThat(allMainMealsBeforeSave).hasSize(allMainMealAfterSave.size() - 1);
    }

    @Test
    void correctlyFindAvailable() {
        Menu menu = saveRestaurantAndOwnerAndMenu();
        // given, when
        List<MainMealEntity> allBefore = mainMealJpaRepository.findAll();
        Set<MainMeal> mainMeals = mainMealRepository.findAvailableByMenu(menu);

        // then
        Assertions.assertThat(mainMeals).hasSize(allBefore.size());
    }

    @Test
    void correctlyMainMealDelete() {
        saveMainMeals();
        // given
        Integer mainMealId = mainMealJpaRepository.findAll().get(0).getMainMealId();

        // when
        List<MainMealEntity> allMainMealsBeforeDelete = mainMealJpaRepository.findAll();
        mainMealRepository.deleteMainMeal(mainMealId);
        List<MainMealEntity> allMainMealsAfterDelete = mainMealJpaRepository.findAll();

        //
        Assertions.assertThat(allMainMealsBeforeDelete).hasSize(allMainMealsAfterDelete.size() + 1);

    }

    @Test
    void correctlyFindByIdMainMeal() {
        saveMainMeals();
        // given
        Integer mainMealId = mainMealJpaRepository.findAll().get(0).getMainMealId();

        // when
        MainMeal mainMeal = mainMealRepository.findById(mainMealId);

        // then
        Assertions.assertThat(mainMeal.getMainMealId()).isEqualTo(mainMealId);
    }

    @Transactional
    @Test
    void correctlyUpdateQuantityMainMeal() {
        saveMainMeals();
        // given
        Integer mainMealId = mainMealJpaRepository.findAll().get(0).getMainMealId();
        Integer quantity = 5;

        // when
        mainMealRepository.updateQuantityMainMeal(mainMealId, quantity);

        // then
        Integer quantityUpdate = mainMealJpaRepository.findById(mainMealId).orElseThrow().getQuantity();
        Assertions.assertThat(quantityUpdate)
                .isEqualTo(quantity);
    }

}
