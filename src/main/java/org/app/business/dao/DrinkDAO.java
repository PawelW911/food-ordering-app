package org.app.business.dao;


import org.app.domain.Drink;
import org.app.infrastructure.database.entity.DrinkEntity;

public interface DrinkDAO {
    Drink saveDrink(Drink drink);
    Integer saveDrinkAndReturnId(DrinkEntity drinkEntity);
}
