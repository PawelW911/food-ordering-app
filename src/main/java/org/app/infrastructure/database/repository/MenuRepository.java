package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.MenuDAO;
import org.app.domain.*;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.repository.jpa.MenuJpaRepository;
import org.app.infrastructure.database.repository.mapper.*;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MenuRepository implements MenuDAO {

    private final MenuJpaRepository menuJpaRepository;
    private final MenuMapper menuMapper;
    private final MainMealMapper mainMealMapper;
    private final AppetizerMapper appetizerMapper;
    private final SoupMapper soupMapper;
    private final DesertMapper desertMapper;
    private final DrinkMapper drinkMapper;
    private final MainMealRepository mainMealRepository;
    private final AppetizerRepository appetizerRepository;
    private final SoupRepository soupRepository;
    private final DesertRepository desertRepository;
    private final DrinkRepository drinkRepository;

    @Override
    public Menu saveMenu(Menu menu) {

        MenuEntity toSave = menuMapper.mapToEntity(menu, mainMealMapper, appetizerMapper, soupMapper, desertMapper, drinkMapper);
        Set<Integer> setWithIdMainMeals = toSave.getMainMeals().stream().map(a -> mainMealRepository.saveMainMealAndReturnId(a)).collect(Collectors.toSet());
        Set<Integer> setWithIdAppetizers = toSave.getAppetizers().stream().map(a -> appetizerRepository.saveAppetizerAndReturnId(a)).collect(Collectors.toSet());
        Set<Integer> setWithIdSoups = toSave.getSoups().stream().map(a -> soupRepository.saveSoupAndReturnId(a)).collect(Collectors.toSet());
        Set<Integer> setWithIdDeserts = toSave.getDeserts().stream().map(a -> desertRepository.saveDesertAndReturnId(a)).collect(Collectors.toSet());
        Set<Integer> setWithIdDrinks = toSave.getDrinks().stream().map(a -> drinkRepository.saveDrinkAndReturnId(a)).collect(Collectors.toSet());

        MenuEntity saved = menuJpaRepository.saveAndFlush(toSave);
//        menuJpaRepository.saveAndFlush(toSave, setWithIdMainMeals, setWithIdAppetizers, setWithIdSoups, setWithIdDeserts, setWithIdDrinks);
        return menuMapper.mapFromEntity(saved);
    }
}
