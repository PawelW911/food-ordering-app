package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.DrinkDAO;
import org.app.domain.Drink;
import org.app.infrastructure.database.entity.DrinkEntity;
import org.app.infrastructure.database.repository.jpa.DrinkJpaRepository;
import org.app.infrastructure.database.repository.mapper.DrinkMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DrinkRepository implements DrinkDAO {

    private final DrinkJpaRepository drinkJpaRepository;
    private final DrinkMapper drinkMapper;

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


}
