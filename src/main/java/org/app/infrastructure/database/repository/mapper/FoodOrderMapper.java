package org.app.infrastructure.database.repository.mapper;

import org.app.domain.*;
import org.app.infrastructure.database.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodOrderMapper {
    default FoodOrderEntity mapToEntity(
            FoodOrder foodOrder,
            RestaurantEntity restaurantEntity,
            CustomerEntity customerEntity

    ) {
        return FoodOrderEntity.builder()
                .foodOrderNumber(foodOrder.getFoodOrderNumber())
                .receivedDateTime(foodOrder.getReceivedDateTime())
                .sumCost(foodOrder.getSumCost())
                .restaurant(restaurantEntity)
                .customer(customerEntity)

                .build();
    }

    @Mapping(target = "restaurant.streetDelivery", ignore = true)
    @Mapping(target = "restaurant.menu", ignore = true)
    @Mapping(target = "restaurant.orders", ignore = true)
    @Mapping(target = "appetizers", ignore = true)
    @Mapping(target = "soups", ignore = true)
    @Mapping(target = "mainMeals", ignore = true)
    @Mapping(target = "deserts", ignore = true)
    @Mapping(target = "drinks", ignore = true)
    FoodOrder mapFromEntity(FoodOrderEntity saved);

    default FoodOrderEntity mapToEntity(FoodOrder foodOrder) {
        return FoodOrderEntity.builder()
                .foodOrderId(foodOrder.getFoodOrderId())
                .foodOrderNumber(foodOrder.getFoodOrderNumber())
                .build();
    }


    default FoodOrder mapFromEntityWithOnlySetDishes(
            String uniqueCode,
            Set<Appetizer> appetizers,
            Set<Soup> soups,
            Set<MainMeal> mainMeals,
            Set<Desert> deserts,
            Set<Drink> drinks
    ) {
        return FoodOrder.builder()
                .restaurant(Restaurant.builder()
                        .uniqueCode(uniqueCode)
                        .build())
                .appetizers(appetizers)
                .soups(soups)
                .mainMeals(mainMeals)
                .deserts(deserts)
                .drinks(drinks)
                .build();
    }
}
