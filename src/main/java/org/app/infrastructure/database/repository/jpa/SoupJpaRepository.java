package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.entity.SoupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoupJpaRepository extends JpaRepository<SoupEntity, Integer> {


    List<SoupEntity> findByMenu(MenuEntity menuEntity);
}
