package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.StreetDeliveryDAO;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;
import org.app.infrastructure.database.repository.jpa.StreetDeliveryJpaRepository;
import org.app.infrastructure.database.repository.mapper.RestaurantMapper;
import org.app.infrastructure.database.repository.mapper.StreetDeliveryMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class StreetDeliveryRepository implements StreetDeliveryDAO {

    private final StreetDeliveryJpaRepository streetDeliveryJpaRepository;
    private final StreetDeliveryMapper streetDeliveryMapper;
    private final RestaurantMapper restaurantMapper;

    @Override
    public Set<StreetDelivery> saveStreetDeliveries(Set<StreetDelivery> streetDeliveries, Restaurant restaurant) {
        List<StreetDeliveryEntity> streetDeliveryEntitiesToSave;

        List<StreetDeliveryEntity> existingStreetDelivery = streetDeliveryJpaRepository.findAll();
        Map<String, List<StreetDeliveryEntity>> streetDeliveryByPostalCode = existingStreetDelivery.stream()
                .collect(Collectors.groupingBy(StreetDeliveryEntity::getPostalCode));
        if (streetDeliveryByPostalCode
                .containsKey(streetDeliveries.stream().toList().get(0).getPostalCode())) {
            List<StreetDeliveryEntity> streetDeliveryEntities = streetDeliveryByPostalCode.get(streetDeliveries.stream()
                    .toList().get(0).getPostalCode()).stream().toList();
            List<RestaurantEntity> restaurantEntities =
                    new ArrayList<>(streetDeliveryEntities.get(0).getRestaurants().stream().toList());
            restaurantEntities.add(restaurantMapper.mapToEntity(restaurant));
            streetDeliveryEntities
                    .forEach(streetDelivery -> streetDelivery.setRestaurants(new HashSet<>(restaurantEntities)));
            streetDeliveryEntitiesToSave = streetDeliveryEntities;
        } else {
            streetDeliveryEntitiesToSave = streetDeliveries.stream()
                    .map(streetDelivery -> streetDeliveryMapper
                            .mapToEntity(streetDelivery, restaurantMapper.mapToEntity(restaurant)))
                    .toList();
        }
        List<StreetDeliveryEntity> streetDeliveryEntitiesSaved =
                streetDeliveryJpaRepository.saveAllAndFlush(streetDeliveryEntitiesToSave);

        return streetDeliveryEntitiesSaved.stream()
                .map(streetDeliveryMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public StreetDeliveryEntity streetDeliveryIsAvailable(StreetDelivery streetDelivery) {
        return streetDeliveryJpaRepository.findByStreetAndCity(streetDelivery.getStreet(), streetDelivery.getCity());
    }

    @Override
    public Set<StreetDelivery> findPostalCodeByRestaurant(Restaurant restaurant) {
        List<StreetDeliveryEntity> streetDeliveryEntities =
                streetDeliveryJpaRepository.findByRestaurants(restaurantMapper.mapToEntity(restaurant));
        return streetDeliveryEntities.stream()
                .map(streetDeliveryMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<StreetDelivery> findByStreetAndCity(String street, String city) {
        return Optional.of(
                streetDeliveryMapper.mapFromEntity(streetDeliveryJpaRepository.findByStreetAndCity(street, city)));
    }

    @Override
    public Set<StreetDelivery> findAvailableStreetsDelivery() {
        return streetDeliveryJpaRepository.findAll().stream()
                .map(streetDeliveryMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }
}
