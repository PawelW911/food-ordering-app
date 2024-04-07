package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.RestaurantDAO;
import org.app.domain.Owner;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.AddressMapper;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.infrastructure.database.repository.mapper.RestaurantMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    RestaurantJpaRepository restaurantJpaRepository;
    OwnerRepository ownerRepository;
    StreetDeliveryRepository streetDeliveryRepository;
    RestaurantMapper restaurantMapper;
    OwnerMapper ownerMapper;
    AddressMapper addressMapper;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        OwnerEntity ownerEntity = ownerRepository.findByEmailAndReturnEntity(restaurant.getOwner().getEmail());
        RestaurantEntity toSave = restaurantMapper.mapToEntity(restaurant,addressMapper.mapToEntity(restaurant.getAddress()), ownerEntity);
        RestaurantEntity saved = restaurantJpaRepository.saveAndFlush(toSave);
        return restaurantMapper.mapFromEntity(saved);
    }

    @Override
    public List<Restaurant> findRestaurantByStreetDelivery(StreetDelivery streetDelivery) {
        List<RestaurantEntity> restaurantEntities =
                restaurantJpaRepository.findByStreetsDelivery(streetDeliveryRepository.streetDeliveryIsAvailable(streetDelivery));
        return restaurantEntities.stream().map(restaurantMapper::mapFromEntity).toList();
    }

    @Override
    public List<Restaurant> findAvailableRestaurantByOwner(Owner ownerExample) {
        List<RestaurantEntity> restaurantEntities =
                restaurantJpaRepository.findByOwner(ownerRepository.findByEmailAndReturnEntity(ownerExample.getEmail()));
        return restaurantEntities.stream().map(restaurantMapper::mapFromEntity).toList();
    }

    @Override
    public Restaurant findByUniqueCode(String uniqueCode) {
        RestaurantEntity byUniqueCode = restaurantJpaRepository.findByUniqueCode(uniqueCode);
        System.out.println(byUniqueCode.getStreetsDelivery());
        return restaurantMapper.mapFromEntity(byUniqueCode);
    }

    @Override
    public List<Restaurant> findAvailableRestaurant() {
        List<RestaurantEntity> restaurantEntities = restaurantJpaRepository.findAll();
        return restaurantEntities.stream()
                .map(restaurantMapper::mapFromEntity)
                .toList();
    }
}
