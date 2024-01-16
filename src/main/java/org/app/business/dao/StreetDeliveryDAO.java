package org.app.business.dao;

import org.app.domain.StreetDelivery;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;

import java.util.Set;

public interface StreetDeliveryDAO {
    Set<StreetDelivery> saveStreetDeliveries(Set<StreetDelivery> streetDeliveries);

    StreetDeliveryEntity streetDeliveryIsAvailable(StreetDelivery streetDelivery);
}
