package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreetDeliveryJpaRepository extends JpaRepository<StreetDeliveryEntity, Integer> {

    StreetDeliveryEntity findByStreetAndCity(String street, String city);

    List<StreetDeliveryEntity> findByRestaurants(RestaurantEntity restaurantEntity);
}
