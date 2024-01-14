package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOrderJpaRepository extends JpaRepository<FoodOrderEntity, Integer> {


}
