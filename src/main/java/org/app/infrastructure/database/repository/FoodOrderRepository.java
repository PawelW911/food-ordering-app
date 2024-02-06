package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.FoodOrderDAO;
import org.app.domain.FoodOrder;
import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.jpa.FoodOrderJpaRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.*;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FoodOrderRepository implements FoodOrderDAO {

    private FoodOrderJpaRepository foodOrderJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private FoodOrderMapper foodOrderMapper;
    private AppetizerMapper appetizerMapper;
    private SoupMapper soupMapper;
    private MainMealMapper mainMealMapper;
    private DesertMapper desertMapper;
    private DrinkMapper drinkMapper;


    @Override
    public FoodOrder saveFoodOrder(FoodOrder foodOrder) {
        FoodOrderEntity toSave = foodOrderMapper.mapToEntity(
                foodOrder,
                restaurantJpaRepository.findByUniqueCode(foodOrder.getRestaurant().getUniqueCode()),
                customerJpaRepository.findByEmail(foodOrder.getCustomer().getEmail())
//                foodOrder.getAppetizers().stream().map(appetizerMapper::mapToEntity).collect(Collectors.toSet()),
//                foodOrder.getSoups().stream().map(soupMapper::mapToEntity).collect(Collectors.toSet()),
//                foodOrder.getMainMeals().stream().map(mainMealMapper::mapToEntity).collect(Collectors.toSet()),
//                foodOrder.getDeserts().stream().map(desertMapper::mapToEntity).collect(Collectors.toSet()),
//                foodOrder.getDrinks().stream().map(drinkMapper::mapToEntity).collect(Collectors.toSet())
                );

        FoodOrderEntity saved = foodOrderJpaRepository.saveAndFlush(toSave);
        return foodOrderMapper.mapFromEntity(saved);
    }
}
