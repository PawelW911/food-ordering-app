package org.app.api.dto.mapper;

import org.app.api.dto.AvailableRestaurantsDTO;
import org.app.api.dto.RestaurantDTO;
import org.app.api.dto.RestaurantRestDTO;
import org.app.domain.Address;
import org.app.domain.Owner;
import org.app.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantDTOMapper {
    default RestaurantDTO mapToDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .uniqueCode(restaurant.getUniqueCode())
                .restaurantName(restaurant.getName())
                .restaurantTypeFood(restaurant.getTypeFood())
                .restaurantEmail(restaurant.getEmail())
                .restaurantPhone(restaurant.getPhone())
                .restaurantOpeningHours(restaurant.getOpeningHours())
                .restaurantAddressStreet(restaurant.getAddress().getStreet())
                .restaurantAddressLocalNumber(restaurant.getAddress().getLocalNumber())
                .restaurantAddressPostalCode(restaurant.getAddress().getPostalCode())
                .restaurantAddressCity(restaurant.getAddress().getCity())
                .build();
    }

    default RestaurantRestDTO mapToRestDTO(Restaurant restaurant) {
        return RestaurantRestDTO.builder()
                .uniqueCode(restaurant.getUniqueCode())
                .name(restaurant.getName())
                .typeFood(restaurant.getTypeFood())
                .email(restaurant.getEmail())
                .phone(restaurant.getPhone())
                .openingHours(restaurant.getOpeningHours())
                .address(Address.builder()
                        .street(restaurant.getAddress().getStreet())
                        .localNumber(restaurant.getAddress().getLocalNumber())
                        .postalCode(restaurant.getAddress().getPostalCode())
                        .city(restaurant.getAddress().getCity())
                        .build())
                .build();
    }

    default Restaurant mapFromDTO(RestaurantDTO restaurantDTO, Owner owner, String uniqueCode) {
        return Restaurant.builder()
                .uniqueCode(uniqueCode)
                .name(restaurantDTO.getRestaurantName())
                .typeFood(restaurantDTO.getRestaurantTypeFood())
                .email(restaurantDTO.getRestaurantEmail())
                .phone(restaurantDTO.getRestaurantPhone())
                .openingHours(restaurantDTO.getRestaurantOpeningHours())
                .address(Address.builder()
                        .street(restaurantDTO.getRestaurantAddressStreet())
                        .localNumber(restaurantDTO.getRestaurantAddressLocalNumber())
                        .postalCode(restaurantDTO.getRestaurantAddressPostalCode())
                        .city(restaurantDTO.getRestaurantAddressCity())
                        .build())
                .owner(owner)
                .build();
    }
}
