package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Integer> {


    RestaurantEntity findByUniqueCode(String uniqueCode);


    List<RestaurantEntity> findByStreetsDelivery(StreetDeliveryEntity streetDeliveryIsAvailable);

    List<RestaurantEntity> findByOwner(OwnerEntity byEmail);

}
