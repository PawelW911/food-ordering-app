package org.app.infrastructure.database.repository.mapper;

import org.app.domain.FoodOrder;
import org.app.infrastructure.database.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodOrderMapper {
    default FoodOrderEntity mapToEntity(
            FoodOrder foodOrder,
            RestaurantEntity restaurantEntity,
            CustomerEntity customerEntity,
            Set<AppetizerEntity> appetizerEntities,
            Set<SoupEntity> soupEntities,
            Set<MainMealEntity> mainMealEntities,
            Set<DesertEntity> desertEntities,
            Set<DrinkEntity> drinkEntities
    ) {
        return FoodOrderEntity.builder()
                .foodOrderNumber(foodOrder.getOrderNumber())
                .receivedDateTime(foodOrder.getReceivedDateTime())
                .sumCost(foodOrder.getSumCost())
                .restaurant(restaurantEntity)
                .customer(customerEntity)
                .appetizers(appetizerEntities)
                .soups(soupEntities)
                .mainMeals(mainMealEntities)
                .deserts(desertEntities)
                .drinks(drinkEntities)
                .build();
    }

    @Mapping(target = "restaurant.streetDelivery", ignore = true)
    @Mapping(target = "restaurant.menu", ignore = true)
    FoodOrder mapFromEntity(FoodOrderEntity saved);
}
