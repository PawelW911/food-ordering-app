package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.StreetDelivery;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;
import org.app.infrastructure.database.repository.jpa.StreetDeliveryJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.app.util.StreetDeliveryFixtures.*;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StreetDeliveryRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final StreetDeliveryRepository streetDeliveryRepository;
    private final StreetDeliveryJpaRepository streetDeliveryJpaRepository;
    

    @Test
    void correctlySaveStreetDelivery() {

        // given
        Set<StreetDelivery> streetDeliveries = someStreetDelivery1();

        // when
        streetDeliveryRepository.saveStreetDeliveries(streetDeliveries);
        List<StreetDeliveryEntity> allStreet = streetDeliveryJpaRepository.findAll();

        // then
        Assertions.assertThat(allStreet).hasSize(streetDeliveries.size());

    }
}
