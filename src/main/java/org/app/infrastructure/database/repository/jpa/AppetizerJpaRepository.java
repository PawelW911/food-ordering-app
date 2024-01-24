package org.app.infrastructure.database.repository.jpa;

import org.app.domain.Menu;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppetizerJpaRepository extends JpaRepository<AppetizerEntity, Integer> {


    List<AppetizerEntity> findByMenu(MenuEntity menuEntity);
}
