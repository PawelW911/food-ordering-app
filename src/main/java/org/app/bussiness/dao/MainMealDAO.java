package org.app.bussiness.dao;

import org.app.domain.MainMeal;
import org.app.domain.Menu;

import java.util.List;
import java.util.Set;

public interface MainMealDAO {
    List<MainMeal> saveMainMeals(List<MainMeal> mainMeal);

    MainMeal saveMainMeal(MainMeal mainMeal);

    Set<MainMeal> findAvailableByMenu(Menu menu);

    void deleteMainMeal(Integer mainMealId);

    MainMeal findById(Integer mainMealId);

    MainMeal updateQuantityMainMeal(Integer mainMealId, Integer quantity);
}
