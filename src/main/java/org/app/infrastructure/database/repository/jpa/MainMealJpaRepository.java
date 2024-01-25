package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.MainMealEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainMealJpaRepository extends JpaRepository<MainMealEntity, Integer> {


    List<MainMealEntity> findByMenu(MenuEntity menuEntity);
}
