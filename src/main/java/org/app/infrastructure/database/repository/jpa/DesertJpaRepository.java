package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.DesertEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesertJpaRepository extends JpaRepository<DesertEntity, Integer> {


    List<DesertEntity> findByMenu(MenuEntity menuEntity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DesertEntity a SET a.quantity = :quantity WHERE a.desertId = :desertId")
    void updateQuantityDesert(
            @Param("desertId") Integer desertId,
            @Param("quantity") Integer quantity
    );
}
