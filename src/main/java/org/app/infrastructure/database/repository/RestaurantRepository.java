package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.RestaurantDAO;
import org.app.domain.Restaurant;
import org.app.infrastructure.database.entity.AddressEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.AddressMapper;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.infrastructure.database.repository.mapper.RestaurantMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    RestaurantJpaRepository restaurantJpaRepository;
    OwnerRepository ownerRepository;
    RestaurantMapper restaurantMapper;
    OwnerMapper ownerMapper;
    AddressMapper addressMapper;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        OwnerEntity ownerEntity = ownerRepository.findByEmail(restaurant.getOwner().getEmail());
        RestaurantEntity toSave = restaurantMapper.mapToEntity(restaurant,addressMapper.mapToEntity(restaurant.getAddress()), ownerEntity);
        RestaurantEntity saved = restaurantJpaRepository.saveAndFlush(toSave);
        return restaurantMapper.mapFromEntity(saved);
    }
}
