package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.DrinkDAO;
import org.app.domain.Drink;
import org.app.domain.Menu;
import org.app.infrastructure.database.entity.DrinkEntity;
import org.app.infrastructure.database.repository.jpa.DrinkJpaRepository;
import org.app.infrastructure.database.repository.mapper.DrinkMapper;
import org.app.infrastructure.database.repository.mapper.MenuMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class DrinkRepository implements DrinkDAO {

    private final DrinkJpaRepository drinkJpaRepository;
    private final DrinkMapper drinkMapper;
    private final MenuMapper menuMapper;

    @Override
    public List<Drink> saveDrinks(List<Drink> drinks) {
        List<DrinkEntity> toSave = drinks.stream()
                .map(drinkMapper::mapToEntity)
                .toList();
        List<DrinkEntity> saved = drinkJpaRepository.saveAllAndFlush(toSave);
        return saved.stream()
                .map(drinkMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Drink saveDrink(Drink drink) {
        DrinkEntity toSave = drinkMapper.mapToEntity(drink);
        DrinkEntity saved = drinkJpaRepository.saveAndFlush(toSave);
        return drinkMapper.mapFromEntity(saved);
    }


    @Override
    public Set<Drink> findAvailableByMenu(Menu menu) {
        List<DrinkEntity> drinkEntities = drinkJpaRepository.findByMenu(menuMapper.mapToEntity(menu));
        return drinkEntities.stream()
                .map(drinkMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteDrink(Integer drinkId) {
        drinkJpaRepository.deleteById(drinkId);
    }


}
