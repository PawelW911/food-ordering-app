package org.app.infrastructure.database.repository.mapper;

import org.app.domain.FoodOrder;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodOrderMapper {
    default FoodOrderEntity mapToEntity(
            FoodOrder foodOrder,
            RestaurantEntity restaurantEntity,
            CustomerEntity customerEntity
    ) {
        return FoodOrderEntity.builder()
                .foodOrderNumber(foodOrder.getOrderNumber())
                .receivedDateTime(foodOrder.getReceivedDateTime())
                .sumCost(foodOrder.getSumCost())
                .restaurant(restaurantEntity)
                .customer(customerEntity)
                .build();
    }

    @Mapping(target = "restaurant.streetDelivery", ignore = true)
    @Mapping(target = "restaurant.menu", ignore = true)
    FoodOrder mapFromEntity(FoodOrderEntity saved);
}
