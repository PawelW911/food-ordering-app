package org.app.bussiness.dao;


import org.app.domain.Customer;
import org.app.domain.FoodOrder;
import org.app.domain.Restaurant;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

public interface FoodOrderDAO {
    FoodOrder saveFoodOrder(FoodOrder foodOrder);

    Set<FoodOrder> findByCustomer(Customer customer);

    FoodOrder findByFoodOrderNumber(String foodOrderNumber, String chooseMapper);

    void updateSumCost(BigDecimal newSumCost, String foodOrderNumber);

    Set<FoodOrder> findAvailableByRestaurant(Restaurant restaurant);

    void updateCompletedDateTime(String foodOrderNumber, OffsetDateTime completedDateTime);
}
