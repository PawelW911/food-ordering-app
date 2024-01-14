package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Drink;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.DrinkEntity;
import org.app.infrastructure.database.repository.jpa.DrinkJpaRepository;
import org.app.util.DrinkFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DrinkRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final DrinkRepository drinkRepository;
    private final DrinkJpaRepository drinkJpaRepository;
    

    @Test
    void correctlySaveDrink() {
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
}
