package org.app.bussiness.dao;


import org.app.domain.Drink;
import org.app.infrastructure.database.entity.DrinkEntity;

import java.util.List;

public interface DrinkDAO {
    List<Drink> saveDrinks(List<Drink> drink);
}
