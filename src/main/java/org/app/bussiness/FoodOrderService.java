package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.api.controller.dataForController.ForFoodOrderChoose;
import org.app.bussiness.dao.FoodOrderDAO;
import org.app.domain.*;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static org.app.api.controller.dataForController.ForFoodOrderChoose.*;

@Slf4j
@Service
@AllArgsConstructor
public class FoodOrderService {

    private final FoodOrderDAO foodOrderDAO;
    private final AppetizerService appetizerService;
    private final MainMealService mainMealService;
    private final DesertService desertService;
    private final DrinkService drinkService;
    private final SoupService soupService;
    private final RestaurantService restaurantService;

    public FoodOrder saveNewFoodOrder(FoodOrder foodOrder) {
        foodOrder.setSumCost(calculateCost(foodOrder));
        FoodOrder foodOrderSaved = saveFoodOrder(foodOrder);
        return assignDishesToTheFoodOrder(foodOrder, foodOrderSaved);
    }

    @Transactional
    private FoodOrder saveFoodOrder(FoodOrder foodOrder) {
        FoodOrder savedFoodOrder = foodOrderDAO.saveFoodOrder(foodOrder);
        if (savedFoodOrder == null) {
            log.error("The attempt to save the food order wasn't a success");
        } else {
            log.info("Save food order a success, id: [{}]", savedFoodOrder.getFoodOrderId());
        }
        return savedFoodOrder;
    }

    public Set<FoodOrder> findFoodOrdersByCustomer(Customer customer) {
        Set<FoodOrder> foodOrders = foodOrderDAO.findByCustomer(customer);
        log.info("Placed food orders by customer with id: [{}], is [{}]", customer.getCustomerId(), foodOrders.size());
        return foodOrders;
    }

    public FoodOrder findByFoodOrderNumber(String foodOrderNumber, String chooseMapper) {
        FoodOrder foodOrder = foodOrderDAO.findByFoodOrderNumber(
                foodOrderNumber,
                chooseMapper
        );
        if (foodOrder == null) {
            throw new NotFoundException(
                    "Food order with order number: [%s] does not exist.".formatted(foodOrderNumber));
        } else {
            log.info("Food order with order number: [{}] is found", foodOrderNumber);
        }
        return foodOrder;
    }

    @Transactional
    public void updateSumCost(String foodOrderNumber) {
        BigDecimal newSumCost = calculateCost(findByFoodOrderNumber(
                foodOrderNumber,
                MAP_ONLY_SET_DISHES.toString()
        ));
        foodOrderDAO.updateSumCost(newSumCost, foodOrderNumber);
        BigDecimal sumCostAfterUpdate = foodOrderDAO.findByFoodOrderNumber(
                foodOrderNumber,
                MAP_WITHOUT_SET_DISHES.toString()
        ).getSumCost();
        if (Objects.equals(sumCostAfterUpdate, newSumCost)) {
            log.info(
                    "Update sum cost: [{}] in food order with order number: [{}] is successfully",
                    newSumCost,
                    foodOrderNumber
            );
        } else {
            log.error("Update sum cost: [{}] in food order with order number: [{}] is failed",
                    newSumCost,
                    foodOrderNumber);
        }
    }

    public Set<FoodOrder> findAvailableByRestaurantUniqueCode(String uniqueCodeRestaurant) {
        Set<FoodOrder> foodOrders = foodOrderDAO
                .findAvailableByRestaurant(restaurantService.findByUniqueCode(uniqueCodeRestaurant));
        log.info(
                "Found food orders in restaurant with unique code: [{}] is [{}]",
                uniqueCodeRestaurant,
                foodOrders.size()
        );
        return foodOrders;
    }

    @Transactional
    public void updateCompletedDateTime(String orderNumber) {
        foodOrderDAO.updateCompletedDateTime(orderNumber, OffsetDateTime.now(ZoneOffset.UTC));
        OffsetDateTime completedDateTime = foodOrderDAO.
                findByFoodOrderNumber(orderNumber, MAP_WITHOUT_SET_DISHES.toString()).getCompletedDateTime();
        if(completedDateTime == null) {
            log.error("Update completed date time in food order with order number: [{}] is failed", orderNumber);
        } else {
            log.info("Update completed date time in food order with order number: [{}] is successfully", orderNumber);
        }
    }

    public BigDecimal calculateCost(FoodOrder foodOrder) {
        BigDecimal costAppetizers = BigDecimal.ZERO;
        for (Appetizer appetizer : foodOrder.getAppetizers()) {
            costAppetizers = costAppetizers.add(appetizer.getPrice().multiply(new BigDecimal(appetizer.getQuantity())));
        }
        BigDecimal costMainMeals = BigDecimal.ZERO;
        for (MainMeal mainMeal : foodOrder.getMainMeals()) {
            costMainMeals = costMainMeals.add(mainMeal.getPrice().multiply(new BigDecimal(mainMeal.getQuantity())));
        }
        BigDecimal costDeserts = BigDecimal.ZERO;
        for (Desert drink : foodOrder.getDeserts()) {
            costDeserts = costDeserts.add(drink.getPrice().multiply(new BigDecimal(drink.getQuantity())));
        }
        BigDecimal costDrinks = BigDecimal.ZERO;
        for (Drink drink : foodOrder.getDrinks()) {
            costDrinks = costDrinks.add(drink.getPrice().multiply(new BigDecimal(drink.getQuantity())));
        }
        BigDecimal costSoups = BigDecimal.ZERO;
        for (Soup soup : foodOrder.getSoups()) {
            costSoups = costSoups.add(soup.getPrice().multiply(new BigDecimal(soup.getQuantity())));
        }

        return Stream.of(costAppetizers, costMainMeals, costDeserts, costDrinks, costSoups)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private FoodOrder assignDishesToTheFoodOrder(FoodOrder foodOrder, FoodOrder foodOrderSaved) {
        return foodOrderSaved
                .withAppetizers(new HashSet<>(appetizerService.saveNewAppetizers(
                        foodOrder.getAppetizers().stream()
                                .map(appetizer -> appetizer.withFoodOrder(foodOrderSaved))
                                .toList())))
                .withMainMeals(new HashSet<>(mainMealService.saveNewMainMeals(
                        foodOrder.getMainMeals().stream()
                                .map(mainMeal -> mainMeal.withFoodOrder(foodOrderSaved))
                                .toList())))
                .withDeserts(new HashSet<>(desertService.saveNewDeserts(
                        foodOrder.getDeserts().stream()
                                .map(desert -> desert.withFoodOrder(foodOrderSaved))
                                .toList())))
                .withDrinks(new HashSet<>(drinkService.saveNewDrinks(
                        foodOrder.getDrinks().stream()
                                .map(drink -> drink.withFoodOrder(foodOrderSaved))
                                .toList())))
                .withSoups(new HashSet<>(soupService.saveNewSoups(
                        foodOrder.getSoups().stream()
                                .map(soup -> soup.withFoodOrder(foodOrderSaved))
                                .toList())));
    }
}
