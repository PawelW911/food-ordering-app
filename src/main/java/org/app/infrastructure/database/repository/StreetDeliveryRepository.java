package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.StreetDeliveryDAO;
import org.app.domain.StreetDelivery;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;
import org.app.infrastructure.database.repository.jpa.StreetDeliveryJpaRepository;
import org.app.infrastructure.database.repository.mapper.StreetDeliveryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class StreetDeliveryRepository implements StreetDeliveryDAO {

    StreetDeliveryJpaRepository streetDeliveryJpaRepository;
    StreetDeliveryMapper streetDeliveryMapper;

    @Override
    public Set<StreetDelivery> saveStreetDeliveries(Set<StreetDelivery> streetDeliveries) {
        Set<StreetDeliveryEntity> streetDeliveryEntitiesToSave = streetDeliveries.stream()
                .map(streetDeliveryMapper::mapToEntity)
                .collect(Collectors.toSet());

        List<StreetDeliveryEntity> streetDeliveryEntitiesSaved =
                streetDeliveryJpaRepository.saveAllAndFlush(streetDeliveryEntitiesToSave);

        return streetDeliveryEntitiesSaved.stream()
                .map(streetDeliveryMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }
}
