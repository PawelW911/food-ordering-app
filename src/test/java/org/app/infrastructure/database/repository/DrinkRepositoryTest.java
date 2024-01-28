package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Drink;
import org.app.domain.Menu;
import org.app.domain.Soup;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.DrinkEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.entity.SoupEntity;
import org.app.infrastructure.database.repository.jpa.DrinkJpaRepository;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DrinkRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final DrinkRepository drinkRepository;
    private final DrinkJpaRepository drinkJpaRepository;
    private final OwnerMapper ownerMapper;
    private final OwnerJpaRepository ownerJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private void saveDrinks() {
        drinkRepository.saveDrink(DrinkFixtures.someDrinksForPolishFood().stream()
                .map(drinkr -> drinkr.withQuantity(0))
                .toList().get(0));
        drinkRepository.saveDrink(DrinkFixtures.someDrinksForPolishFood().stream()
                .map(drinkr -> drinkr.withQuantity(0))
                .toList().get(1));

    }

    private Menu saveRestaurantAndOwnerAndMenu() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
        Menu menu = menuRepository.saveMenu(MenuFixtures.someMenuPolishFood());
        drinkRepository.saveDrink(DrinkFixtures.someDrinksForPolishFood().stream()
                .map(drinkr -> drinkr.withQuantity(0).withMenu(menu))
                .toList().get(0));
        drinkRepository.saveDrink(DrinkFixtures.someDrinksForPolishFood().stream()
                .map(drinkr -> drinkr.withQuantity(0).withMenu(menu))
                .toList().get(1));

        return menu;
    }

    @Test
    void correctlySaveDrinks() {
        // given
        List<Drink> drinks = DrinkFixtures.someDrinksForPolishFood().stream()
                .map(drink -> drink.withQuantity(0))
                .toList();

        // when
        List<DrinkEntity> allDrinkBeforeSave = drinkJpaRepository.findAll();
        drinkRepository.saveDrinks(drinks);
        List<DrinkEntity> allDrinkAfterSave = drinkJpaRepository.findAll();

        // then
        Assertions.assertThat(allDrinkBeforeSave).hasSize(allDrinkAfterSave.size() - drinks.size());
    }

    @Test
    void correctlyDrinkSave() {
        // given
        Drink drink = DrinkFixtures.someDrinksForPolishFood().stream()
                .map(drinkr -> drinkr.withQuantity(0))
                .toList().get(0);

        // when
        List<DrinkEntity> allDrinksBeforeSave = drinkJpaRepository.findAll();
        drinkRepository.saveDrink(drink);
        List<DrinkEntity> allDrinkAfterSave = drinkJpaRepository.findAll();

        // then
        Assertions.assertThat(allDrinksBeforeSave).hasSize(allDrinkAfterSave.size() - 1);
    }

    @Test
    void correctlyFindAvailable() {
        Menu menu = saveRestaurantAndOwnerAndMenu();
        // given, when
        List<DrinkEntity> allBefore = drinkJpaRepository.findAll();
        Set<Drink> drinks = drinkRepository.findAvailableByMenu(menu);

        // then
        Assertions.assertThat(drinks).hasSize(allBefore.size());
    }

    @Test
    void correctlyDrinkDelete() {
        saveDrinks();
        // given
        Integer drinkId = drinkJpaRepository.findAll().get(0).getDrinkId();

        // when
        List<DrinkEntity> allDrinksBeforeDelete = drinkJpaRepository.findAll();
        drinkRepository.deleteDrink(drinkId);
        List<DrinkEntity> allDrinksAfterDelete = drinkJpaRepository.findAll();

        //
        Assertions.assertThat(allDrinksBeforeDelete).hasSize(allDrinksAfterDelete.size() + 1);

    }
}
