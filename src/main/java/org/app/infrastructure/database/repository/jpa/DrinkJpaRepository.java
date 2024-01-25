package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.DrinkEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkJpaRepository extends JpaRepository<DrinkEntity, Integer> {


    List<DrinkEntity> findByMenu(MenuEntity menuEntity);
}
