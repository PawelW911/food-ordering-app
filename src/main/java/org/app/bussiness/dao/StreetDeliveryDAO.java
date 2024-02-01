package org.app.bussiness.dao;

import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;

import java.util.Set;

public interface StreetDeliveryDAO {
    Set<StreetDelivery> saveStreetDeliveries(Set<StreetDelivery> streetDeliveries, Restaurant waw45);

    StreetDeliveryEntity streetDeliveryIsAvailable(StreetDelivery streetDelivery);

    Set<StreetDelivery> findPostalCodeByRestaurant(Restaurant restaurant);
}
