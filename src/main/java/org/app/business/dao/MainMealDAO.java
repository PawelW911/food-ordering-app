package org.app.business.dao;

import org.app.domain.MainMeal;
import org.app.infrastructure.database.entity.MainMealEntity;

public interface MainMealDAO {
    MainMeal saveMainMeal(MainMeal mainMeal);
    Integer saveMainMealAndReturnId(MainMealEntity mainMealEntity);
}
