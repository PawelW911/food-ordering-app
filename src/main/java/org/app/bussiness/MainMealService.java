package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.MainMealDAO;
import org.app.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class MainMealService {

    private final MainMealDAO mainMealDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<MainMeal> saveNewMainMeals(List<MainMeal> mainMeals) {
        return mainMealDAO.saveMainMeals(mainMeals);
    }

    @Transactional
    public MainMeal saveNewMainMeal(MainMeal mainMeal) {
        return mainMealDAO.saveMainMeal(mainMeal);
    }


    public Set<MainMeal> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        return mainMealDAO.findAvailableByMenu(menu);
    }

    @Transactional
    public void deleteMainMeal(Integer mainMealId) {
        mainMealDAO.deleteMainMeal(mainMealId);
    }

    public MainMeal findById(Integer mainMealId) {
        return mainMealDAO.findById(mainMealId);
    }

    @Transactional
    public MainMeal updateQuantityMainMeal(Integer mainMealId, Integer quantity) {
        return mainMealDAO.updateQuantityMainMeal(mainMealId, quantity);
    }
}
