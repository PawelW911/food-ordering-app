package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.MainMealDAO;
import org.app.domain.MainMeal;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class MainMealService {

    private final MainMealDAO mainMealDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<MainMeal> saveNewMainMeals(List<MainMeal> mainMeals) {
        List<MainMeal> mainMealList = mainMealDAO.saveMainMeals(mainMeals);
        if (mainMealList.size() == mainMeals.size()) {
            log.info("[{}] main meals save a success", mainMeals.size());
        } else {
            log.error("[{}] the attempt to save the main meals wasn't a success", mainMeals.size());
        }
        return mainMealList;
    }

    @Transactional
    public MainMeal saveNewMainMeal(MainMeal mainMeal) {
        MainMeal savedMainMeal = mainMealDAO.saveMainMeal(mainMeal);
        if (savedMainMeal == null) {
            log.error("The attempt to save the main meal wasn't a success");
        } else {
            log.info("Save main meal a success, id: [{}]", savedMainMeal.getMainMealId());
        }
        return savedMainMeal;
    }


    public Set<MainMeal> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        Set<MainMeal> mainMeals = mainMealDAO.findAvailableByMenu(menu);
        log.info("Available main meals in restaurant [{}] is [{}]", restaurant, mainMeals.size());
        return mainMeals;
    }

    @Transactional
    public void deleteMainMeal(Integer mainMealId) {
        mainMealDAO.deleteMainMeal(mainMealId);
        if (mainMealDAO.findById(mainMealId) == null) {
            log.info("Main meal with id: [{}] delete is success.", mainMealId);
        } else {
            log.error("Main meal with id: [{}] is didn't successfully.", mainMealId);
        }
    }

    public MainMeal findById(Integer mainMealId) {
        MainMeal mainMeal = mainMealDAO.findById(mainMealId);
        if (mainMeal == null) {
            throw new NotFoundException("Main meal with id: [%s] does not exist.".formatted(mainMealId));
        } else {
            log.info("Main meal with id: [{}] is found", mainMealId);
        }
        return mainMeal;
    }

    @Transactional
    public MainMeal updateQuantityMainMeal(Integer mainMealId, Integer quantity) {
        MainMeal mainMeal = mainMealDAO.updateQuantityMainMeal(mainMealId, quantity);
        if (!(mainMeal == null)) {
            if (Objects.equals(mainMeal.getQuantity(), quantity)) {
                log.info("Update quantity: [{}], in main meal with id: [{}]", quantity, mainMealId);
            } else {
                log.error("Quantity: [{}], has not been updated, in main meal with id: [{}]", quantity, mainMealId);
            }
        } else {
            log.info("Main meal with id: [{}] is delete", mainMealId);
        }
        return mainMeal;
    }
}
