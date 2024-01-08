package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.DrinkDAO;
import org.app.domain.Drink;
import org.app.infrastructure.database.entity.DrinkEntity;
import org.app.infrastructure.database.repository.jpa.DrinkJpaRepository;
import org.app.infrastructure.database.repository.mapper.DrinkMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DrinkRepository implements DrinkDAO {

    private final DrinkJpaRepository drinkJpaRepository;
    private final DrinkMapper drinkMapper;

    @Override
    public Drink saveDrink(Drink drink) {
        DrinkEntity toSave = drinkMapper.mapToEntity(drink);
        DrinkEntity saved = drinkJpaRepository.saveAndFlush(toSave);
        return drinkMapper.mapFromEntity(saved);
    }

    @Override
    public Integer saveDrinkAndReturnId(DrinkEntity drinkEntity) {
        return drinkJpaRepository.saveAndFlush(drinkEntity).getDrinkId();
    }

}
