package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.FoodOrderDAO;
import org.app.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FoodOrderService {

    private final FoodOrderDAO foodOrderDAO;
    private final AppetizerService appetizerService;
    private final MainMealService mainMealService;
    private final DesertService desertService;
    private final DrinkService drinkService;
    private final SoupService soupService;

    public FoodOrder saveNewFoodOrder(FoodOrder foodOrder) {
        foodOrder.setSumCost(calculateCost(foodOrder));
        FoodOrder foodOrderSaved = saveOrder(foodOrder);
        System.out.println(foodOrderSaved.getOrderId());
        if(foodOrderSaved == null) {
            throw new RuntimeException("Food order doesn't save");
        }
        return assignDishesToTheFoodOrder(foodOrder, foodOrderSaved);
    }

    @Transactional
    private FoodOrder saveOrder(FoodOrder foodOrder) {
        return foodOrderDAO.saveFoodOrder(foodOrder);
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
