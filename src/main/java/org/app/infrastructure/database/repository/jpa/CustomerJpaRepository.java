package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> {


    CustomerEntity findByEmail(String email);
}
