package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.DrinkDAO;
import org.app.domain.Drink;
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
public class DrinkService {

    private final DrinkDAO drinkDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<Drink> saveNewDrinks(List<Drink> drinks) {
        List<Drink> saveDrinks = drinkDAO.saveDrinks(drinks);
        if (saveDrinks.size() == drinks.size()) {
            log.info("[{}] drinks save a success", drinks.size());
        } else {
            log.error("[{}] the attempt to save the drinks wasn't a success", drinks.size());
        }
        return saveDrinks;
    }

    @Transactional
    public Drink saveNewDrink(Drink drink) {
        Drink savedDrink = drinkDAO.saveDrink(drink);
        if (savedDrink == null) {
            log.error("The attempt to save the drink wasn't a success");
        } else {
            log.info("Save drink a success, id: [{}]", savedDrink.getDrinkId());
        }
        return savedDrink;
    }


    public Set<Drink> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        Set<Drink> drinkSet = drinkDAO.findAvailableByMenu(menu);
        log.info("Available drinks in restaurant [{}] is [{}]", restaurant, drinkSet.size());
        return drinkSet;
    }

    @Transactional
    public void deleteDrink(Integer drinkId) {
        drinkDAO.deleteDrink(drinkId);
        if (drinkDAO.findById(drinkId) == null) {
            log.info("Drink with id: [{}] delete is success.", drinkId);
        } else {
            log.error("Drink with id: [{}] is didn't successfully.", drinkId);
        }
    }

    public Drink findById(Integer drinkId) {
        Drink drink = drinkDAO.findById(drinkId);
        if (drink == null) {
            throw new NotFoundException("Drink with id: [%s] does not exist.".formatted(drinkId));
        } else {
            log.info("Drink with id: [{}] is found", drinkId);
        }
        return drink;
    }

    @Transactional
    public Drink updateQuantityDrink(Integer drinkId, Integer quantity) {
        Drink drink = drinkDAO.updateQuantityDrink(drinkId, quantity);
        if (!(drink == null)) {
            if (Objects.equals(drink.getQuantity(), quantity)) {
                log.info("Update quantity: [{}], in drink with id: [{}]", quantity, drinkId);
            } else {
                log.error("Quantity: [{}], has not been updated, in drink with id: [{}]", quantity, drinkId);
            }
        } else {
            log.info("Drink with id: [{}] is delete", drinkId);
        }
        return drink;
    }
}
