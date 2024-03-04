package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.OpinionEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionJpaRepository extends JpaRepository<OpinionEntity, Integer> {


    List<OpinionEntity> findByRestaurant(RestaurantEntity byUniqueCode);
}
