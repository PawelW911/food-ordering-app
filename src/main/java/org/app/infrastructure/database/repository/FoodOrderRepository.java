package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.FoodOrderDAO;
import org.app.domain.FoodOrder;
import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.jpa.FoodOrderJpaRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.FoodOrderMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class FoodOrderRepository implements FoodOrderDAO {

    FoodOrderJpaRepository foodOrderJpaRepository;
    RestaurantJpaRepository restaurantJpaRepository;
    CustomerJpaRepository customerJpaRepository;
    FoodOrderMapper foodOrderMapper;

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
}
