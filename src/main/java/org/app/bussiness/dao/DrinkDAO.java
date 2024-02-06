package org.app.bussiness.dao;


import org.app.domain.Drink;
import org.app.domain.Menu;
import org.app.infrastructure.database.entity.DrinkEntity;

import java.util.List;
import java.util.Set;

public interface DrinkDAO {
    List<Drink> saveDrinks(List<Drink> drink);

    Drink saveDrink(Drink drink);

    Set<Drink> findAvailableByMenu(Menu menu);

    void deleteDrink(Integer drinkId);

    Drink findById(Integer drinkId);
}
