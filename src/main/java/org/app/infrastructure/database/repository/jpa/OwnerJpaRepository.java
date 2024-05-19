package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Integer> {

    OwnerEntity findByEmail(String email);
}
