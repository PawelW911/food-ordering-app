package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.StreetDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetDeliveryJpaRepository extends JpaRepository<StreetDeliveryEntity, Integer> {



    StreetDeliveryEntity findByStreetAndCity(String street, String city);
}
