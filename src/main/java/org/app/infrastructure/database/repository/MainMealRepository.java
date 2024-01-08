package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.MainMealDAO;
import org.app.domain.MainMeal;
import org.app.infrastructure.database.entity.MainMealEntity;
import org.app.infrastructure.database.repository.jpa.MainMealJpaRepository;
import org.app.infrastructure.database.repository.mapper.MainMealMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MainMealRepository implements MainMealDAO {

    private final MainMealJpaRepository mainMealJpaRepository;
    private final MainMealMapper mainMealMapper;

    @Override
    public MainMeal saveMainMeal(MainMeal mainMeal) {
        MainMealEntity toSave = mainMealMapper.mapToEntity(mainMeal);
        MainMealEntity saved = mainMealJpaRepository.saveAndFlush(toSave);
        return mainMealMapper.mapFromEntity(saved);
    }

    @Override
    public Integer saveMainMealAndReturnId(MainMealEntity mainMealEntity) {
        return mainMealJpaRepository.saveAndFlush(mainMealEntity).getMainMealId();
    }

}
