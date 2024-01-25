package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.DesertEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesertJpaRepository extends JpaRepository<DesertEntity, Integer> {


    List<DesertEntity> findByMenu(MenuEntity menuEntity);
}
