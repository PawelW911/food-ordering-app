package org.app.business.dao;

import org.app.domain.MainMeal;
import org.app.infrastructure.database.entity.MainMealEntity;

import java.util.List;

public interface MainMealDAO {
    List<MainMeal> saveMainMeals(List<MainMeal> mainMeal);
}
