package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.MainMeal;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.MainMealEntity;
import org.app.infrastructure.database.repository.jpa.MainMealJpaRepository;
import org.app.util.MainMealFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MainMealRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final MainMealRepository mainMealRepository;
    private final MainMealJpaRepository mainMealJpaRepository;
    

    @Test
    void correctlySaveMainMeal() {
        
        // given
        List<MainMeal> mainMeals = MainMealFixtures.someMainMealForPolishFood().stream()
                .map(mainMeal -> mainMeal.withQuantity(0))
                .toList();

        // when
        List<MainMealEntity> allMainMealBeforeSave = mainMealJpaRepository.findAll();
        mainMealRepository.saveMainMeals(mainMeals);
        List<MainMealEntity> allMainMealAfterSave = mainMealJpaRepository.findAll();

        // then
        Assertions.assertThat(allMainMealBeforeSave).hasSize(allMainMealAfterSave.size() - mainMeals.size());
    }



}
