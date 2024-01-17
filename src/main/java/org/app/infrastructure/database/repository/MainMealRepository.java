package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.MainMealDAO;
import org.app.domain.MainMeal;
import org.app.infrastructure.database.entity.MainMealEntity;
import org.app.infrastructure.database.repository.jpa.MainMealJpaRepository;
import org.app.infrastructure.database.repository.mapper.MainMealMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MainMealRepository implements MainMealDAO {

    private final MainMealJpaRepository mainMealJpaRepository;
    private final MainMealMapper mainMealMapper;

    @Override
    public List<MainMeal> saveMainMeals(List<MainMeal> mainMeals) {
        List<MainMealEntity> toSave = mainMeals.stream()
                .map(mainMealMapper::mapToEntity)
                .toList();
        List<MainMealEntity> saved = mainMealJpaRepository.saveAllAndFlush(toSave);
        return saved.stream()
                .map(mainMealMapper::mapFromEntity)
                .toList();
    }

}
