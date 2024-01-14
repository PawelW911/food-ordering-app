package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Restaurant;
import org.app.infrastructure.database.entity.AddressEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    default RestaurantEntity mapToEntity(Restaurant restaurant, AddressEntity addressEntity, OwnerEntity ownerEntity) {
        return RestaurantEntity.builder()
                .uniqueCode(restaurant.getUniqueCode())
                .name(restaurant.getName())
                .typeFood(restaurant.getTypeFood())
                .email(restaurant.getEmail())
                .phone(restaurant.getPhone())
                .openingHours(restaurant.getOpeningHours())
                .address(addressEntity)
                .owner(ownerEntity)
                .build();
    }

    Restaurant mapFromEntity(RestaurantEntity restaurantEntity);

    default Restaurant mapFromEntity(RestaurantEntity restaurantEntity, AddressMapper addressMapper, OwnerMapper ownerMapper) {
        return Restaurant.builder()
                .restaurantId(restaurantEntity.getRestaurantId())
                .uniqueCode(restaurantEntity.getUniqueCode())
                .name(restaurantEntity.getName())
                .typeFood(restaurantEntity.getTypeFood())
                .email(restaurantEntity.getEmail())
                .phone(restaurantEntity.getPhone())
                .openingHours(restaurantEntity.getOpeningHours())
                .address(addressMapper.mapFromEntity(restaurantEntity.getAddress()))
                .owner(ownerMapper.mapFromEntity(restaurantEntity.getOwner()))
                .build();
    }
}
