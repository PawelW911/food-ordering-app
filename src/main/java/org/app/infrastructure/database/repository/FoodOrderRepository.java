package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.api.controller.dataForController.ForFoodOrderChoose;
import org.app.bussiness.dao.FoodOrderDAO;
import org.app.domain.Customer;
import org.app.domain.FoodOrder;
import org.app.domain.Restaurant;
import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.jpa.FoodOrderJpaRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FoodOrderRepository implements FoodOrderDAO {

    private final FoodOrderJpaRepository foodOrderJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final CustomerJpaRepository customerJpaRepository;
    private final FoodOrderMapper foodOrderMapper;
    private final CustomerMapper customerMapper;
    private final AppetizerMapper appetizerMapper;
    private final SoupMapper soupMapper;
    private final MainMealMapper mainMealMapper;
    private final DesertMapper desertMapper;
    private final DrinkMapper drinkMapper;
    private final RestaurantMapper restaurantMapper;


    @Override
    public FoodOrder saveFoodOrder(FoodOrder foodOrder) {
        FoodOrderEntity toSave = foodOrderMapper.mapToEntity(
                foodOrder,
                restaurantJpaRepository.findByUniqueCode(foodOrder.getRestaurant().getUniqueCode()),
                customerJpaRepository.findByEmail(foodOrder.getCustomer().getEmail())
        );

        FoodOrderEntity saved = foodOrderJpaRepository.saveAndFlush(toSave);
        return foodOrderMapper.mapFromEntity(saved);
    }

    @Override
    public Set<FoodOrder> findByCustomer(Customer customer) {
        Set<FoodOrderEntity> foodOrderEntities =
                foodOrderJpaRepository.findByCustomer(customerMapper.mapToEntity(customer));
        return foodOrderEntities.stream()
                .map(foodOrderMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public FoodOrder findByFoodOrderNumber(String foodOrderNumber, String chooseMapper) {
        FoodOrderEntity byFoodOrderNumber = foodOrderJpaRepository.findByFoodOrderNumber(foodOrderNumber);

        if (chooseMapper.equalsIgnoreCase(ForFoodOrderChoose.MAP_ONLY_SET_DISHES.toString())) {
            return foodOrderMapper
                    .mapFromEntityWithOnlySetDishes(
                            byFoodOrderNumber.getRestaurant().getUniqueCode(),
                            byFoodOrderNumber.getAppetizers().stream()
                                    .map(appetizerMapper::mapFromEntity)
                                    .collect(Collectors.toSet()),
                            byFoodOrderNumber.getSoups().stream()
                                    .map(soupMapper::mapFromEntity)
                                    .collect(Collectors.toSet()),
                            byFoodOrderNumber.getMainMeals().stream()
                                    .map(mainMealMapper::mapFromEntity)
                                    .collect(Collectors.toSet()),
                            byFoodOrderNumber.getDeserts().stream()
                                    .map(desertMapper::mapFromEntity)
                                    .collect(Collectors.toSet()),
                            byFoodOrderNumber.getDrinks().stream()
                                    .map(drinkMapper::mapFromEntity)
                                    .collect(Collectors.toSet())
                    );
        } else {
            return foodOrderMapper.mapFromEntity(byFoodOrderNumber);
        }
    }

    @Override
    public void updateSumCost(BigDecimal newSumCost, String foodOrderNumber) {
        foodOrderJpaRepository.updateSumCost(newSumCost, foodOrderNumber);
    }

    @Override
    public Set<FoodOrder> findAvailableByRestaurant(Restaurant restaurant) {
        Set<FoodOrderEntity> foodOrderEntities = foodOrderJpaRepository
                .findByRestaurant(restaurantMapper.mapToEntity(restaurant));
        return foodOrderEntities.stream()
                .filter(a -> a.getCompletedDateTime() == null)
                .map(foodOrderMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public void updateCompletedDateTime(String foodOrderNumber, OffsetDateTime completedDateTime) {
        foodOrderJpaRepository.updateCompletedDateTime(completedDateTime, foodOrderNumber);
    }
}
