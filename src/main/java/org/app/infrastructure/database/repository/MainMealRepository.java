package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.MainMealDAO;
import org.app.domain.MainMeal;
import org.app.domain.Menu;
import org.app.infrastructure.database.entity.MainMealEntity;
import org.app.infrastructure.database.repository.jpa.MainMealJpaRepository;
import org.app.infrastructure.database.repository.mapper.MainMealMapper;
import org.app.infrastructure.database.repository.mapper.MenuMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MainMealRepository implements MainMealDAO {

    private final MainMealJpaRepository mainMealJpaRepository;
    private final MainMealMapper mainMealMapper;
    private final MenuMapper menuMapper;

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

    @Override
    public MainMeal saveMainMeal(MainMeal mainMeal) {
        MainMealEntity toSave = mainMealMapper.mapToEntity(mainMeal);
        MainMealEntity saved = mainMealJpaRepository.saveAndFlush(toSave);
        return mainMealMapper.mapFromEntity(saved);
    }


    @Override
    public Set<MainMeal> findAvailableByMenu(Menu menu) {
        List<MainMealEntity> mainMealEntities = mainMealJpaRepository.findByMenu(menuMapper.mapToEntity(menu));
        return mainMealEntities.stream()
                .map(mainMealMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteMainMeal(Integer mainMealId) {
        mainMealJpaRepository.deleteById(mainMealId);
    }

    @Override
    public MainMeal findById(Integer mainMealId) {
        return mainMealMapper.mapFromEntity(mainMealJpaRepository.findById(mainMealId).orElseThrow());
    }

}
