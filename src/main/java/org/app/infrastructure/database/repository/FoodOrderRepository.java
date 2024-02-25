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

    private FoodOrderJpaRepository foodOrderJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private FoodOrderMapper foodOrderMapper;
    private CustomerMapper customerMapper;
    private AppetizerMapper appetizerMapper;
    private SoupMapper soupMapper;
    private MainMealMapper mainMealMapper;
    private DesertMapper desertMapper;
    private DrinkMapper drinkMapper;
    private RestaurantMapper restaurantMapper;


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
        if (foodOrderEntities != null) {
            System.out.println("non-null");
        }
        return foodOrderEntities.stream()
                .map(foodOrder -> foodOrderMapper.mapFromEntity(foodOrder))
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
                                    .map(a -> appetizerMapper.mapFromEntity(a))
                                    .collect(Collectors.toSet()),
                            byFoodOrderNumber.getSoups().stream()
                                    .map(a -> soupMapper.mapFromEntity(a))
                                    .collect(Collectors.toSet()),
                            byFoodOrderNumber.getMainMeals().stream()
                                    .map(a -> mainMealMapper.mapFromEntity(a))
                                    .collect(Collectors.toSet()),
                            byFoodOrderNumber.getDeserts().stream()
                                    .map(a -> desertMapper.mapFromEntity(a))
                                    .collect(Collectors.toSet()),
                            byFoodOrderNumber.getDrinks().stream()
                                    .map(a -> drinkMapper.mapFromEntity(a))
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
