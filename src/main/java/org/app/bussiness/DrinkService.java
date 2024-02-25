package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.DrinkDAO;
import org.app.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DrinkService {

    private final DrinkDAO drinkDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<Drink> saveNewDrinks(List<Drink> drinks) {
        return drinkDAO.saveDrinks(drinks);
    }

    @Transactional
    public Drink saveNewDrink(Drink drink) {
        return drinkDAO.saveDrink(drink);
    }


    public Set<Drink> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        return drinkDAO.findAvailableByMenu(menu);
    }

    @Transactional
    public void deleteDrink(Integer drinkId) {
        drinkDAO.deleteDrink(drinkId);
    }

    public Drink findById(Integer drinkId) {
        return drinkDAO.findById(drinkId);
    }

    @Transactional
    public Drink updateQuantityDrink(Integer drinkId, Integer quantity) {
        return drinkDAO.updateQuantityDrink(drinkId, quantity);
    }
}
