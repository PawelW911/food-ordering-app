package org.app.infrastructure.database.repository.mapper;

import org.app.domain.StreetDelivery;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StreetDeliveryMapper {
    default StreetDeliveryEntity mapToEntity(StreetDelivery streetDelivery, RestaurantEntity restaurantEntity) {
        return StreetDeliveryEntity.builder()
                .street(streetDelivery.getStreet())
                .city(streetDelivery.getCity())
                .postalCode(streetDelivery.getPostalCode())
                .restaurants(Set.of(restaurantEntity))
                .build();
    }

    StreetDelivery mapFromEntity(StreetDeliveryEntity streetDeliveryEntity);
}
