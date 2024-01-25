package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.MainMealDAO;
import org.app.domain.MainMeal;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.domain.Soup;
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
}
