package org.app.bussiness.dao;


import org.app.domain.Drink;
import org.app.domain.Menu;

import java.util.List;
import java.util.Set;

public interface DrinkDAO {
    List<Drink> saveDrinks(List<Drink> drink);

    Drink saveDrink(Drink drink);

    Set<Drink> findAvailableByMenu(Menu menu);

    void deleteDrink(Integer drinkId);

    Drink findById(Integer drinkId);

    Drink updateQuantityDrink(Integer drinkId, Integer quantity);
}
